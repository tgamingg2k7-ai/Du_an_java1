<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">

<jsp:include page="/Layouts/sidebar.jsp"/>
<jsp:include page="/Layouts/header.jsp"/>

<div class="main">

    <h2 class="page-title">QUẢN LÝ KHUYẾN MÃI</h2>

    <div class="card form-inline-card">

        <form action="${pageContext.request.contextPath}/promotion" method="post">
            <input type="hidden" name="action" value="${editPromotion == null ? 'add' : 'update'}">

            <div class="form-grid">
                <div class="form-group">
                    <label>Mã KM</label>
                    <input name="ma" value="${editPromotion.ma}" ${editPromotion == null ? '' : 'readonly'} required>
                </div>

                <div class="form-group">
                    <label>Tên khuyến mãi</label>
                    <input name="ten" value="${editPromotion.ten}" required>
                </div>

                <div class="form-group">
                    <label>Loại giảm</label>
                    <select name="loaiGiam">
                        <option value="Phần trăm">Phần trăm</option>
                        <option value="Giá trị">Giá trị</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Giá trị</label>
                    <input name="giaTri" value="${editPromotion.giaTri}" required>
                </div>

                <div class="form-group">
                    <label>Ngày bắt đầu</label>
                    <input name="ngayBatDau" value="${editPromotion.ngayBatDau}">
                </div>

                <div class="form-group">
                    <label>Ngày kết thúc</label>
                    <input name="ngayKetThuc" value="${editPromotion.ngayKetThuc}">
                </div>

                <div class="form-group">
                    <label>Trạng thái</label>
                    <select name="trangThai">
                        <option value="Đang hoạt động">Đang hoạt động</option>
                        <option value="Đã kết thúc">Đã kết thúc</option>
                        <option value="Sắp diễn ra">Sắp diễn ra</option>
                    </select>
                </div>
            </div>

            <div class="form-action">
                <button class="btn-save" type="submit">
                    ${editPromotion == null ? 'Thêm khuyến mãi' : 'Cập nhật'}
                </button>
                <a class="btn-cancel" href="${pageContext.request.contextPath}/promotion">Làm mới</a>
            </div>
        </form>

    </div>

    <div class="table-box">
        <table>
            <thead>
            <tr>
                <th>Mã KM</th>
                <th>Tên khuyến mãi</th>
                <th>Loại giảm</th>
                <th>Giá trị</th>
                <th>Ngày bắt đầu</th>
                <th>Ngày kết thúc</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="p" items="${promotions}">
                <tr>
                    <td>${p.ma}</td>
                    <td>${p.ten}</td>
                    <td>${p.loaiGiam}</td>
                    <td>${p.giaTri}</td>
                    <td>${p.ngayBatDau}</td>
                    <td>${p.ngayKetThuc}</td>
                    <td>
                        <span class="status ${p.trangThai == 'Đang hoạt động' ? 'success' : 'danger'}">
                                ${p.trangThai}
                        </span>
                    </td>
                    <td class="action">
                        <a href="${pageContext.request.contextPath}/promotion?action=edit&ma=${p.ma}">✎</a>
                        <a onclick="return confirm('Xóa khuyến mãi này?')"
                           href="${pageContext.request.contextPath}/promotion?action=delete&ma=${p.ma}">🗑</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>

<script src="${pageContext.request.contextPath}/assets/main.js"></script>