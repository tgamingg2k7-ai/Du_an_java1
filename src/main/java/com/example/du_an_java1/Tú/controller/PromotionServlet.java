package com.example.du_an_java1.Tú.controller;

import com.example.du_an_java1.Tú.Repository.PromotionRepository;
import com.example.du_an_java1.Tú.model.Promotion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/promotion")
public class PromotionServlet extends HttpServlet {

    private final PromotionRepository promotionRepository = new PromotionRepository();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            promotionRepository.delete(request.getParameter("ma"));
            response.sendRedirect(request.getContextPath() + "/promotion");
            return;
        }

        if ("edit".equals(action)) {
            request.setAttribute("editPromotion", promotionRepository.findByMa(request.getParameter("ma")));
        }

        request.setAttribute("promotions", promotionRepository.getAll());

        request.getRequestDispatcher("/views/promotion.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        Promotion promotion = new Promotion(
                request.getParameter("ma"),
                request.getParameter("ten"),
                request.getParameter("loaiGiam"),
                request.getParameter("giaTri"),
                request.getParameter("ngayBatDau"),
                request.getParameter("ngayKetThuc"),
                request.getParameter("trangThai")
        );

        if ("update".equals(action)) {
            promotionRepository.update(promotion);
        } else {
            promotionRepository.add(promotion);
        }

        response.sendRedirect(request.getContextPath() + "/promotion");
    }
}