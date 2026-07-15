<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">

<jsp:include page="/Layouts/sidebar.jsp"/>
<jsp:include page="/Layouts/header.jsp"/>

<div class="main">

    <h2 class="page-title">QUẢN LÝ KHÁCH HÀNG</h2>

    <div class="card form-inline-card">

        <form action="${pageContext.request.contextPath}/customer" method="post">
            <input type="hidden" name="action" value="${editCustomer == null ? 'add' : 'update'}">

            <div class="form-grid">
                <div class="form-group">
                    <label>Mã KH</label>
                    <input name="ma" value="${editCustomer.ma}" ${editCustomer == null ? '' : 'readonly'} required>
                </div>

                <div class="form-group">
                    <label>Họ tên</label>
                    <input name="hoTen" value="${editCustomer.hoTen}" required>
                </div>

                <div class="form-group">
                    <label>SĐT</label>
                    <input name="sdt" value="${editCustomer.sdt}" required>
                </div>

                <div class="form-group">
                    <label>Email</label>
                    <input name="email" value="${editCustomer.email}">
                </div>

                <div class="form-group">
                    <label>Địa chỉ</label>
                    <input name="diaChi" value="${editCustomer.diaChi}">
                </div>

                <div class="form-group">
                    <label>Ngày tạo</label>
                    <input name="ngayTao" value="${editCustomer.ngayTao}">
                </div>
            </div>

            <div class="form-action">
                <button class="btn-save" type="submit">
                    ${editCustomer == null ? 'Thêm khách hàng' : 'Cập nhật'}
                </button>
                <a class="btn-cancel" href="${pageContext.request.contextPath}/customer">Làm mới</a>
            </div>
        </form>

    </div>

    <div class="toolbar">
        <span></span>
        <form action="${pageContext.request.contextPath}/customer" method="get">
            <input type="text" name="keyword" placeholder="Tìm kiếm khách hàng..." value="${keyword}">
            <button type="submit">Tìm</button>
        </form>
    </div>

    <div class="table-box">
        <table>
            <thead>
            <tr>
                <th>Mã KH</th>
                <th>Họ tên</th>
                <th>SĐT</th>
                <th>Email</th>
                <th>Địa chỉ</th>
                <th>Ngày tạo</th>
                <th>Thao tác</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="c" items="${customers}">
                <tr>
                    <td>${c.ma}</td>
                    <td>${c.hoTen}</td>
                    <td>${c.sdt}</td>
                    <td>${c.email}</td>
                    <td>${c.diaChi}</td>
                    <td>${c.ngayTao}</td>
                    <td class="action">
                        <a href="${pageContext.request.contextPath}/customer?action=edit&ma=${c.ma}">✎</a>
                        <a onclick="return confirm('Xóa khách hàng này?')"
                           href="${pageContext.request.contextPath}/customer?action=delete&ma=${c.ma}">🗑</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>

<script src="${pageContext.request.contextPath}/assets/main.js"></script>