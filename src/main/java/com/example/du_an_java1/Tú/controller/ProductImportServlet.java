package com.example.du_an_java1.Tú.controller;

import com.example.du_an_java1.Tú.Repository.ProductRepository;
import com.example.du_an_java1.Tú.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/product-import")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 10 * 1024 * 1024,
        maxRequestSize = 15 * 1024 * 1024
)
public class ProductImportServlet extends HttpServlet {

    private ProductRepository productRepository;

    @Override
    public void init() {
        productRepository = new ProductRepository();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.getRequestDispatcher("/views/product-import.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        Part filePart = request.getPart("excelFile");

        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute(
                    "error",
                    "Bạn chưa chọn file Excel."
            );

            doGet(request, response);
            return;
        }

        String fileName = filePart.getSubmittedFileName();

        if (fileName == null
                || (!fileName.toLowerCase().endsWith(".xlsx")
                && !fileName.toLowerCase().endsWith(".xls"))) {

            request.setAttribute(
                    "error",
                    "Chỉ chấp nhận file Excel .xlsx hoặc .xls."
            );

            doGet(request, response);
            return;
        }

        int successCount = 0;
        int errorCount = 0;

        List<String> errors = new ArrayList<>();

        try (
                InputStream inputStream = filePart.getInputStream();
                Workbook workbook =
                        WorkbookFactory.create(inputStream)
        ) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            /*
             * Bắt đầu từ dòng 1 vì dòng 0 là tiêu đề.
             */
            for (
                    int rowIndex = 1;
                    rowIndex <= sheet.getLastRowNum();
                    rowIndex++
            ) {

                Row row = sheet.getRow(rowIndex);

                if (row == null || isEmptyRow(row, formatter)) {
                    continue;
                }

                try {
                    Product product =
                            readProductFromRow(row, formatter);

                    /*
                     * Có thể thêm kiểm tra trùng mã sản phẩm tại đây.
                     */
                    productRepository.insert(product);

                    successCount++;

                } catch (Exception exception) {
                    errorCount++;

                    errors.add(
                            "Dòng " + (rowIndex + 1)
                                    + ": "
                                    + exception.getMessage()
                    );
                }
            }

            request.setAttribute(
                    "success",
                    "Nhập thành công "
                            + successCount
                            + " sản phẩm."
            );

            request.setAttribute(
                    "errorCount",
                    errorCount
            );

            request.setAttribute(
                    "importErrors",
                    errors
            );

        } catch (Exception exception) {
            exception.printStackTrace();

            request.setAttribute(
                    "error",
                    "Không thể đọc file Excel: "
                            + exception.getMessage()
            );
        }

        request.getRequestDispatcher("/views/product-import.jsp")
                .forward(request, response);
    }

    private Product readProductFromRow(
            Row row,
            DataFormatter formatter
    ) {

        String maSanPham =
                getCellValue(row, 0, formatter);

        String tenSP =
                getCellValue(row, 1, formatter);

        String maDMText =
                cleanNumber(getCellValue(row, 2, formatter));

        String maTHText =
                cleanNumber(getCellValue(row, 3, formatter));

        String maNCCText =
                cleanNumber(getCellValue(row, 4, formatter));

        String giaBanText =
                cleanMoney(getCellValue(row, 5, formatter));

        String moTa =
                getCellValue(row, 6, formatter);

        String chatLieu =
                getCellValue(row, 7, formatter);

        String gioiTinh =
                getCellValue(row, 8, formatter);

        String trangThaiText =
                getCellValue(row, 9, formatter);

        if (maSanPham.isEmpty()) {
            throw new IllegalArgumentException(
                    "Mã sản phẩm không được để trống"
            );
        }

        if (tenSP.isEmpty()) {
            throw new IllegalArgumentException(
                    "Tên sản phẩm không được để trống"
            );
        }

        if (maDMText.isEmpty()) {
            throw new IllegalArgumentException(
                    "Mã danh mục không được để trống"
            );
        }

        if (maTHText.isEmpty()) {
            throw new IllegalArgumentException(
                    "Mã thương hiệu không được để trống"
            );
        }

        if (giaBanText.isEmpty()) {
            throw new IllegalArgumentException(
                    "Giá bán không được để trống"
            );
        }

        int maDM;

        int maTH;

        Integer maNCC = null;

        BigDecimal giaBan;

        try {
            maDM = Integer.parseInt(maDMText);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Mã danh mục không hợp lệ"
            );
        }

        try {
            maTH = Integer.parseInt(maTHText);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Mã thương hiệu không hợp lệ"
            );
        }

        if (!maNCCText.isEmpty()) {
            try {
                maNCC = Integer.parseInt(maNCCText);
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException(
                        "Mã nhà cung cấp không hợp lệ"
                );
            }
        }

        try {
            giaBan = new BigDecimal(giaBanText);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Giá bán không hợp lệ"
            );
        }

        if (giaBan.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Giá bán không được nhỏ hơn 0"
            );
        }

        boolean trangThai =
                parseTrangThai(trangThaiText);

        Product product = new Product();

        product.setMaSanPham(maSanPham);
        product.setTenSP(tenSP);
        product.setMaDM(maDM);
        product.setMaTH(maTH);
        product.setMaNCC(maNCC);
        product.setGiaBan(giaBan);
        product.setMoTa(moTa);
        product.setChatLieu(chatLieu);
        product.setGioiTinh(gioiTinh);
        product.setTrangThai(trangThai);

        return product;
    }

    private String getCellValue(
            Row row,
            int index,
            DataFormatter formatter
    ) {
        return formatter
                .formatCellValue(row.getCell(index))
                .trim();
    }

    private String cleanNumber(String value) {
        return value
                .replace(",", "")
                .replace(".0", "")
                .trim();
    }

    private String cleanMoney(String value) {
        return value
                .replace(",", "")
                .replace("đ", "")
                .replace("₫", "")
                .trim();
    }

    private boolean parseTrangThai(String value) {

        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        String normalized = value.trim();

        return normalized.equalsIgnoreCase("Đang bán")
                || normalized.equalsIgnoreCase("Hoạt động")
                || normalized.equalsIgnoreCase("Còn bán")
                || normalized.equalsIgnoreCase("true")
                || normalized.equalsIgnoreCase("yes")
                || normalized.equals("1");
    }

    private boolean isEmptyRow(
            Row row,
            DataFormatter formatter
    ) {

        for (int index = 0; index < 10; index++) {
            String value =
                    formatter
                            .formatCellValue(row.getCell(index))
                            .trim();

            if (!value.isEmpty()) {
                return false;
            }
        }

        return true;
    }
}