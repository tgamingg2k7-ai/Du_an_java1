<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">

<jsp:include page="/Layouts/sidebar.jsp"/>
<jsp:include page="/Layouts/header.jsp"/>

<div class="main">

    <h2 class="page-title">QUẢN LÝ SẢN PHẨM</h2>

    <div class="toolbar">
        <a class="btn-add" href="${pageContext.request.contextPath}/product?action=add">
            + Thêm sản phẩm
        </a>
        <a class="btn-import-excel"
           href="${pageContext.request.contextPath}/product-import">
            Nhập từ Excel
        </a>

        <form action="${pageContext.request.contextPath}/product" method="get">
            <input type="text"
                   name="keyword"
                   placeholder="Tìm mã hoặc tên sản phẩm..."
                   value="${keyword}">

            <select name="category">
                <option value="">Tất cả danh mục</option>
                <c:forEach var="dm" items="${listDanhMuc}">
                    <option value="${dm.TenDanhMuc}" ${category == dm.TenDanhMuc ? 'selected' : ''}>
                            ${dm.TenDanhMuc}
                    </option>
                </c:forEach>
            </select>

            <select name="status">
                <option value="">Tất cả trạng thái</option>
                <option value="1" ${status == '1' ? 'selected' : ''}>Đang bán</option>
                <option value="0" ${status == '0' ? 'selected' : ''}>Ngừng bán</option>
            </select>

            <button type="submit">Tìm</button>
        </form>
    </div>

    <div class="table-box">
        <table>
            <thead>
            <tr>
                <th>Mã SP</th>
                <th>Tên sản phẩm</th>
                <th>Danh mục</th>
                <th>Thương hiệu</th>
                <th>Giá</th>
                <th>Số lượng</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="p" items="${listProduct}">
                <tr>
                    <td>${p.maSanPham}</td>
                    <td>${p.tenSP}</td>
                    <td>${p.tenDanhMuc}</td>
                    <td>${p.tenThuongHieu}</td>
                    <td>${p.giaBan} đ</td>
                    <td>${p.tongSoLuong}</td>
                    <td>
                        <c:choose>
                            <c:when test="${p.trangThai}">
                                <span class="status success">Đang bán</span>
                            </c:when>
                            <c:otherwise>
                                <span class="status danger">Ngừng bán</span>
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <td class="action">
                        <a href="${pageContext.request.contextPath}/product?action=detail&id=${p.maSP}">
                            👁
                        </a>

                        <a href="${pageContext.request.contextPath}/product?action=edit&id=${p.maSP}">
                            ✎
                        </a>

                        <a onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này?')"
                           href="${pageContext.request.contextPath}/product?action=delete&id=${p.maSP}">
                            🗑
                        </a>
                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty listProduct}">
                <tr>
                    <td colspan="8" style="text-align:center;">
                        Không có sản phẩm nào
                    </td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>

</div>

<script src="${pageContext.request.contextPath}/assets/main.js"></script>