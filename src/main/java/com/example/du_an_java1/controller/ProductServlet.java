package com.example.du_an_java1.controller;

import com.example.du_an_java1.Repository.ProductRepository;
import com.example.du_an_java1.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    private final ProductRepository productRepository = new ProductRepository();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "add":
                showAddForm(request, response);
                break;

            case "edit":
                showEditForm(request, response);
                break;

            case "detail":
                showDetail(request, response);
                break;

            case "delete":
                deleteProduct(request, response);
                break;

            default:
                showList(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("update".equals(action)) {
            updateProduct(request, response);
        } else {
            addProduct(request, response);
        }
    }

    private void showList(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String category = request.getParameter("category");
        String status = request.getParameter("status");

        List<Product> listProduct = productRepository.search(keyword, category, status);

        request.setAttribute("listProduct", listProduct);
        request.setAttribute("listDanhMuc", productRepository.getAllDanhMuc());

        request.setAttribute("keyword", keyword);
        request.setAttribute("category", category);
        request.setAttribute("status", status);

        request.getRequestDispatcher("/views/product.jsp")
                .forward(request, response);
    }

    private void showAddForm(HttpServletRequest request,
                             HttpServletResponse response)
            throws ServletException, IOException {

        loadFormData(request);

        request.getRequestDispatcher("/views/product-add.jsp")
                .forward(request, response);
    }

    private void addProduct(HttpServletRequest request,
                            HttpServletResponse response)
            throws IOException {

        Product product = getProductFromRequest(request);

        productRepository.add(product);

        response.sendRedirect(request.getContextPath() + "/product");
    }

    private void showEditForm(HttpServletRequest request,
                              HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        Product product = productRepository.findById(id);

        request.setAttribute("product", product);
        loadFormData(request);

        request.getRequestDispatcher("/views/product-edit.jsp")
                .forward(request, response);
    }

    private void updateProduct(HttpServletRequest request,
                               HttpServletResponse response)
            throws IOException {

        Product product = getProductFromRequest(request);

        productRepository.update(product);

        response.sendRedirect(request.getContextPath() + "/product");
    }

    private void showDetail(HttpServletRequest request,
                            HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        Product product = productRepository.findById(id);

        request.setAttribute("product", product);

        request.getRequestDispatcher("/views/product-detail.jsp")
                .forward(request, response);
    }

    private void deleteProduct(HttpServletRequest request,
                               HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        productRepository.delete(id);

        response.sendRedirect(request.getContextPath() + "/product");
    }

    private void loadFormData(HttpServletRequest request) {
        request.setAttribute("listDanhMuc", productRepository.getAllDanhMuc());
        request.setAttribute("listThuongHieu", productRepository.getAllThuongHieu());
        request.setAttribute("listNhaCungCap", productRepository.getAllNhaCungCap());
    }

    private Product getProductFromRequest(HttpServletRequest request) {
        Product product = new Product();

        String maSPRaw = request.getParameter("maSP");
        if (maSPRaw != null && !maSPRaw.trim().isEmpty()) {
            product.setMaSP(Integer.parseInt(maSPRaw));
        }

        product.setMaSanPham(request.getParameter("maSanPham"));
        product.setTenSP(request.getParameter("tenSP"));

        product.setMaDM(Integer.parseInt(request.getParameter("maDM")));
        product.setMaTH(Integer.parseInt(request.getParameter("maTH")));

        String maNCCRaw = request.getParameter("maNCC");
        if (maNCCRaw != null && !maNCCRaw.trim().isEmpty()) {
            product.setMaNCC(Integer.parseInt(maNCCRaw));
        } else {
            product.setMaNCC(null);
        }

        product.setGiaBan(new BigDecimal(request.getParameter("giaBan")));
        product.setMoTa(request.getParameter("moTa"));
        product.setChatLieu(request.getParameter("chatLieu"));
        product.setGioiTinh(request.getParameter("gioiTinh"));

        String trangThai = request.getParameter("trangThai");
        product.setTrangThai("1".equals(trangThai));

        return product;
    }
}