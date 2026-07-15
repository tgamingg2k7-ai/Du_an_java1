package com.example.du_an_java1.Tú.controller;

import com.example.du_an_java1.Tú.Repository.AttributeRepository;
import com.example.du_an_java1.Tú.model.AttributeItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/attribute")
public class AttributeServlet extends HttpServlet {

    private final AttributeRepository attributeRepository = new AttributeRepository();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String type = request.getParameter("type");

        if (action == null) {
            action = "color";
        }

        if ("delete".equals(action)) {
            attributeRepository.delete(request.getParameter("ma"));
            response.sendRedirect(request.getContextPath() + "/attribute?action=" + type);
            return;
        }

        if ("edit".equals(action)) {
            AttributeItem editAttribute = attributeRepository.findByMa(request.getParameter("ma"));
            request.setAttribute("editAttribute", editAttribute);
            action = editAttribute.getLoai();
        }

        String loai = normalizeType(action);

        request.setAttribute("type", loai);
        request.setAttribute("attributes", attributeRepository.getByLoai(loai));

        request.getRequestDispatcher("/views/attribute.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        String formAction = request.getParameter("formAction");
        String loai = request.getParameter("loai");

        AttributeItem item = new AttributeItem(
                request.getParameter("ma"),
                request.getParameter("ten"),
                request.getParameter("giaTri"),
                loai,
                request.getParameter("trangThai")
        );

        if ("update".equals(formAction)) {
            attributeRepository.update(item);
        } else {
            attributeRepository.add(item);
        }

        response.sendRedirect(request.getContextPath() + "/attribute?action=" + loai);
    }

    private String normalizeType(String action) {
        if ("category".equals(action)) return "category";
        if ("brand".equals(action)) return "brand";
        if ("size".equals(action)) return "size";
        if ("variant".equals(action)) return "variant";
        return "color";
    }
}