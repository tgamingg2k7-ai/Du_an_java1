package com.example.du_an_java1.controller;

import com.example.du_an_java1.Repository.CustomerRepository;
import com.example.du_an_java1.model.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {

    private final CustomerRepository customerRepository = new CustomerRepository();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            customerRepository.delete(request.getParameter("ma"));
            response.sendRedirect(request.getContextPath() + "/customer");
            return;
        }

        if ("edit".equals(action)) {
            Customer editCustomer = customerRepository.findByMa(request.getParameter("ma"));
            request.setAttribute("editCustomer", editCustomer);
        }

        String keyword = request.getParameter("keyword");

        List<Customer> customers;

        if (keyword != null && !keyword.trim().isEmpty()) {
            customers = customerRepository.search(keyword);
        } else {
            customers = customerRepository.getAll();
        }

        request.setAttribute("customers", customers);
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher("/views/customer.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        Customer customer = getCustomerFromRequest(request);

        if ("update".equals(action)) {
            customerRepository.update(customer);
        } else {
            customerRepository.add(customer);
        }

        response.sendRedirect(request.getContextPath() + "/customer");
    }

    private Customer getCustomerFromRequest(HttpServletRequest request) {
        String ma = request.getParameter("ma");
        String hoTen = request.getParameter("hoTen");
        String sdt = request.getParameter("sdt");
        String email = request.getParameter("email");
        String diaChi = request.getParameter("diaChi");

        String ngayTao = request.getParameter("ngayTao");

        if (ngayTao == null || ngayTao.trim().isEmpty()) {
            ngayTao = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        return new Customer(ma, hoTen, sdt, email, diaChi, ngayTao);
    }
}