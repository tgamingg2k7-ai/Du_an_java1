package com.example.du_an_java1.controller;

import com.example.du_an_java1.Repository.InvoiceRepository;
import com.example.du_an_java1.Repository.ProductRepository;
import com.example.du_an_java1.model.CartItem;
import com.example.du_an_java1.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/sale")
public class SaleServlet extends HttpServlet {

    private final ProductRepository productRepository = new ProductRepository();
    private final InvoiceRepository invoiceRepository = new InvoiceRepository();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        prepareSalePage(request);

        request.getRequestDispatcher("/views/sale.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        List<CartItem> cart = getCart(session);

        if ("add".equals(action)) {
            addProductToCart(request, response, cart);
            return;
        }

        if ("remove".equals(action)) {
            removeProductFromCart(request, response, cart);
            return;
        }

        if ("clear".equals(action)) {
            cart.clear();
            session.setAttribute("message", "Đã xóa toàn bộ giỏ hàng.");
            response.sendRedirect(request.getContextPath() + "/sale");
            return;
        }

        if ("pay".equals(action)) {
            payCart(request, response, session, cart);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/sale");
    }

    private void prepareSalePage(HttpServletRequest request) {
        HttpSession session = request.getSession();

        List<CartItem> cart = getCart(session);

        request.setAttribute("products", productRepository.getAll());
        request.setAttribute("cart", cart);
        request.setAttribute("total", calculateTotal(cart));

        Object message = session.getAttribute("message");

        if (message != null) {
            request.setAttribute("message", message);
            session.removeAttribute("message");
        }
    }

    private void addProductToCart(HttpServletRequest request,
                                  HttpServletResponse response,
                                  List<CartItem> cart)
            throws IOException {

        String idRaw = request.getParameter("id");
        String soLuongRaw = request.getParameter("soLuong");

        if (idRaw == null || idRaw.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/sale");
            return;
        }

        int id = Integer.parseInt(idRaw);

        int soLuong = 1;
        if (soLuongRaw != null && !soLuongRaw.trim().isEmpty()) {
            soLuong = Integer.parseInt(soLuongRaw);
        }

        if (soLuong <= 0) {
            soLuong = 1;
        }

        Product product = productRepository.findById(id);

        if (product != null) {
            addToCart(cart, product, soLuong);
        }

        response.sendRedirect(request.getContextPath() + "/sale");
    }

    private void removeProductFromCart(HttpServletRequest request,
                                       HttpServletResponse response,
                                       List<CartItem> cart)
            throws IOException {

        String idRaw = request.getParameter("id");

        if (idRaw != null && !idRaw.trim().isEmpty()) {
            int id = Integer.parseInt(idRaw);

            cart.removeIf(item ->
                    item.getProduct() != null
                            && item.getProduct().getMaSP() == id
            );
        }

        response.sendRedirect(request.getContextPath() + "/sale");
    }

    private void payCart(HttpServletRequest request,
                         HttpServletResponse response,
                         HttpSession session,
                         List<CartItem> cart)
            throws IOException {

        String khachHang = request.getParameter("khachHang");

        if (khachHang == null || khachHang.trim().isEmpty()) {
            khachHang = "Khách lẻ";
        }

        BigDecimal total = calculateTotal(cart);

        if (cart.isEmpty() || total.compareTo(BigDecimal.ZERO) <= 0) {
            session.setAttribute("message", "Giỏ hàng đang trống.");
            response.sendRedirect(request.getContextPath() + "/sale");
            return;
        }

        boolean success = invoiceRepository.createFromSale(khachHang, total, cart);

        if (success) {
            cart.clear();
            session.setAttribute("message", "Thanh toán thành công, hóa đơn đã được tạo.");
        } else {
            session.setAttribute("message", "Thanh toán thất bại. Kiểm tra lại tồn kho hoặc dữ liệu sản phẩm.");
        }

        response.sendRedirect(request.getContextPath() + "/sale");
    }

    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        return cart;
    }

    private void addToCart(List<CartItem> cart, Product product, int soLuong) {
        for (CartItem item : cart) {
            if (item.getProduct() != null
                    && item.getProduct().getMaSP() == product.getMaSP()) {

                item.setSoLuong(item.getSoLuong() + soLuong);
                return;
            }
        }

        cart.add(new CartItem(product, soLuong));
    }

    private BigDecimal calculateTotal(List<CartItem> cart) {
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cart) {
            if (item != null && item.getThanhTien() != null) {
                total = total.add(item.getThanhTien());
            }
        }

        return total;
    }
}