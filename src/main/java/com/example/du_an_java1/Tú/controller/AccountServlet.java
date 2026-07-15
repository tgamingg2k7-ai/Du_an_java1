package com.example.du_an_java1.Tú.controller;

import com.example.du_an_java1.Tú.Repository.AccountRepository;
import com.example.du_an_java1.Tú.model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {

    private final AccountRepository accountRepository = new AccountRepository();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            accountRepository.delete(request.getParameter("ma"));
            response.sendRedirect(request.getContextPath() + "/account");
            return;
        }

        if ("edit".equals(action)) {
            Account editAccount = accountRepository.findByMa(request.getParameter("ma"));
            request.setAttribute("editAccount", editAccount);
        }

        String keyword = request.getParameter("keyword");

        List<Account> accounts;

        if (keyword != null && !keyword.trim().isEmpty()) {
            accounts = accountRepository.search(keyword);
        } else {
            accounts = accountRepository.getAll();
        }

        request.setAttribute("accounts", accounts);
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher("/views/account.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        Account account = new Account(
                request.getParameter("ma"),
                request.getParameter("hoTen"),
                request.getParameter("username"),
                request.getParameter("vaiTro"),
                request.getParameter("sdt"),
                request.getParameter("trangThai")
        );

        if ("update".equals(action)) {
            accountRepository.update(account);
        } else {
            accountRepository.add(account);
        }

        response.sendRedirect(request.getContextPath() + "/account");
    }
}