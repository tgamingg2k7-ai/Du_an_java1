<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">

<jsp:include page="/Layouts/sidebar.jsp"/>
<jsp:include page="/Layouts/header.jsp"/>

<div class="main">

    <h2 class="page-title">QUẢN LÝ TÀI KHOẢN</h2>

    <div class="card form-inline-card">

        <form action="${pageContext.request.contextPath}/account" method="post">
            <input type="hidden" name="action" value="${editAccount == null ? 'add' : 'update'}">

            <div class="form-grid">
                <div class="form-group">
                    <label>Mã TK</label>
                    <input name="ma" value="${editAccount.ma}" ${editAccount == null ? '' : 'readonly'} required>
                </div>

                <div class="form-group">
                    <label>Họ tên</label>
                    <input name="hoTen" value="${editAccount.hoTen}" required>
                </div>

                <div class="form-group">
                    <label>Username</label>
                    <input name="username" value="${editAccount.username}" required>
                </div>

                <div class="form-group">
                    <label>Vai trò</label>
                    <select name="vaiTro">
                        <option value="Quản trị viên">Quản trị viên</option>
                        <option value="Nhân viên bán hàng">Nhân viên bán hàng</option>
                        <option value="Nhân viên kho">Nhân viên kho</option>
                        <option value="Kế toán">Kế toán</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>SĐT</label>
                    <input name="sdt" value="${editAccount.sdt}">
                </div>

                <div class="form-group">
                    <label>Trạng thái</label>
                    <select name="trangThai">
                        <option value="Hoạt động">Hoạt động</option>
                        <option value="Không hoạt động">Không hoạt động</option>
                    </select>
                </div>
            </div>

            <div class="form-action">
                <button class="btn-save" type="submit">
                    ${editAccount == null ? 'Thêm tài khoản' : 'Cập nhật'}
                </button>
                <a class="btn-cancel" href="${pageContext.request.contextPath}/account">Làm mới</a>
            </div>
        </form>

    </div>

    <div class="toolbar">
        <span></span>
        <form action="${pageContext.request.contextPath}/account" method="get">
            <input type="text" name="keyword" placeholder="Tìm tài khoản..." value="${keyword}">
            <button type="submit">Tìm</button>
        </form>
    </div>

    <div class="table-box">
        <table>
            <thead>
            <tr>
                <th>Mã TK</th>
                <th>Họ tên</th>
                <th>Username</th>
                <th>Vai trò</th>
                <th>SĐT</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="a" items="${accounts}">
                <tr>
                    <td>${a.ma}</td>
                    <td>${a.hoTen}</td>
                    <td>${a.username}</td>
                    <td>${a.vaiTro}</td>
                    <td>${a.sdt}</td>
                    <td>
                        <span class="status ${a.trangThai == 'Hoạt động' ? 'success' : 'danger'}">
                                ${a.trangThai}
                        </span>
                    </td>
                    <td class="action">
                        <a href="${pageContext.request.contextPath}/account?action=edit&ma=${a.ma}">✎</a>
                        <a onclick="return confirm('Xóa tài khoản này?')"
                           href="${pageContext.request.contextPath}/account?action=delete&ma=${a.ma}">🗑</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>

<script src="${pageContext.request.contextPath}/assets/main.js"></script>