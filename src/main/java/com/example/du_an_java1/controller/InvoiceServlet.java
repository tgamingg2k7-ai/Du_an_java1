package com.example.du_an_java1.controller;

import com.example.du_an_java1.Repository.InvoiceRepository;
import com.example.du_an_java1.model.Invoice;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/invoice")
public class InvoiceServlet extends HttpServlet {

    private final InvoiceRepository invoiceRepository = new InvoiceRepository();

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
            case "detail":
                showDetail(request, response);
                break;

            case "delete":
                deleteInvoice(request, response);
                break;

            default:
                showList(request, response);
                break;
        }
    }

    private void showList(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");

        List<Invoice> invoices = invoiceRepository.search(keyword);

        request.setAttribute("invoices", invoices);
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher("/views/invoice.jsp")
                .forward(request, response);
    }

    private void showDetail(HttpServletRequest request,
                            HttpServletResponse response)
            throws ServletException, IOException {

        String idRaw = request.getParameter("id");

        if (idRaw != null && !idRaw.trim().isEmpty()) {
            int id = Integer.parseInt(idRaw);

            Invoice selectedInvoice = invoiceRepository.findById(id);

            request.setAttribute("selectedInvoice", selectedInvoice);
        }

        showList(request, response);
    }

    private void deleteInvoice(HttpServletRequest request,
                               HttpServletResponse response)
            throws IOException {

        String idRaw = request.getParameter("id");

        if (idRaw != null && !idRaw.trim().isEmpty()) {
            int id = Integer.parseInt(idRaw);

            invoiceRepository.delete(id);
        }

        response.sendRedirect(request.getContextPath() + "/invoice");
    }
}