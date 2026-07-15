package com.example.du_an_java1.controller;

import com.example.du_an_java1.Repository.InvoiceRepository;
import com.example.du_an_java1.Repository.ProductRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private final InvoiceRepository invoiceRepository = new InvoiceRepository();
    private final ProductRepository productRepository = new ProductRepository();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("invoices", invoiceRepository.getAll());
        request.setAttribute("productCount", productRepository.getAll().size());

        request.getRequestDispatcher("/views/dashboard.jsp")
                .forward(request, response);
    }
}