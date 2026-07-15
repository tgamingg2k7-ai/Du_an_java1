package com.example.du_an_java1.Filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        // Cho phép truy cập trang login và tài nguyên tĩnh
        if (uri.endsWith("/login")
                || uri.contains("/assets/")
                || uri.contains("/images/")
                || uri.contains(".css")
                || uri.contains(".js")
                || uri.contains(".png")
                || uri.contains(".jpg")
                || uri.contains(".jpeg")
                || uri.contains(".gif")) {

            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("user") != null) {

            chain.doFilter(request, response);

        } else {

            res.sendRedirect(req.getContextPath() + "/login");

        }

    }
}