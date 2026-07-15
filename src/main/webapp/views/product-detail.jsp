<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">

<jsp:include page="/Layouts/sidebar.jsp"/>
<jsp:include page="/Layouts/header.jsp"/>

<div class="main">

    <h2 class="page-title">CHI TIẾT SẢN PHẨM</h2>

    <div class="detail-box">

        <p><strong>Mã sản phẩm:</strong> ${product.maSanPham}</p>
        <p><strong>Tên sản phẩm:</strong> ${product.tenSP}</p>
        <p><strong>Danh mục:</strong> ${product.tenDanhMuc}</p>
        <p><strong>Thương hiệu:</strong> ${product.tenThuongHieu}</p>
        <p><strong>Nhà cung cấp:</strong> ${product.tenNCC}</p>
        <p><strong>Giá bán:</strong> ${product.giaBan} đ</p>
        <p><strong>Chất liệu:</strong> ${product.chatLieu}</p>
        <p><strong>Giới tính:</strong> ${product.gioiTinh}</p>
        <p><strong>Số lượng tồn:</strong> ${product.tongSoLuong}</p>

        <p>
            <strong>Trạng thái:</strong>
            <c:choose>
                <c:when test="${product.trangThai}">
                    <span class="status success">Đang bán</span>
                </c:when>
                <c:otherwise>
                    <span class="status danger">Ngừng bán</span>
                </c:otherwise>
            </c:choose>
        </p>

        <p><strong>Mô tả:</strong></p>
        <p>${product.moTa}</p>

        <div class="form-action">
            <a class="btn-add" href="${pageContext.request.contextPath}/product?action=edit&id=${product.maSP}">
                Sửa sản phẩm
            </a>

            <a href="${pageContext.request.contextPath}/product">
                Quay lại
            </a>
        </div>

    </div>

</div>

<script src="${pageContext.request.contextPath}/assets/main.js"></script>