<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">

<jsp:include page="/Layouts/sidebar.jsp"/>
<jsp:include page="/Layouts/header.jsp"/>

<div class="main">

    <h2 class="page-title">TỔNG QUAN</h2>
    <p class="page-desc">Chào mừng trở lại đây là số liệu hôm nay!</p>

    <div class="stat-grid">

        <div class="stat-card">
            <div class="stat-icon green">💵</div>
            <div>
                <p>Doanh Thu Tháng</p>
                <h3>15.530.000đ</h3>
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-icon pink">🛒</div>
            <div>
                <p>Đơn Hàng Mới</p>
                <h3>100</h3>
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-icon purple">👤</div>
            <div>
                <p>Khách Hàng</p>
                <h3>85</h3>
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-icon red">📦</div>
            <div>
                <p>Sản Phẩm Tồn Kho</p>
                <h3>${productCount}</h3>
            </div>
        </div>

    </div>

    <div class="card chart-card">
        <div class="card-title-row">
            <div>
                <h3>Biểu Đồ Doanh Thu</h3>
                <p>Hiệu suất bán hàng trong 30 ngày qua</p>
            </div>

            <div class="switch-btn">
                <button>Tuần</button>
                <button class="active">Tháng</button>
            </div>
        </div>

        <div class="bar-chart">
            <c:forEach var="h" items="${[80,35,42,68,82,10,15,28,58,96,66,45,60,55,57,70,18,27,62,43,56,45,69,85,74,34,65,14,11,77]}">
                <div class="bar-wrap">
                    <div class="bar" style="height:${h}%"></div>
                </div>
            </c:forEach>
        </div>
    </div>

    <div class="card">
        <div class="section-title">
            <span>📋</span>
            <h3>Đơn Hàng Gần Đây</h3>
        </div>

        <table>
            <thead>
            <tr>
                <th>Mã ĐH</th>
                <th>Khách hàng</th>
                <th>Ngày đặt</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="i" items="${invoices}">
                <tr>
                    <td class="code-red">${i.ma}</td>
                    <td>${i.khachHang}</td>
                    <td>${i.ngayTao}</td>
                    <td>${i.tongTien}đ</td>
                    <td>
                        <span class="status ${i.trangThai == 'Hoàn thành' ? 'success' : 'danger'}">
                                ${i.trangThai}
                        </span>
                    </td>
                    <td class="action">
                        <a href="${pageContext.request.contextPath}/invoice?action=detail&ma=${i.ma}">👁</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>

<script src="${pageContext.request.contextPath}/assets/main.js"></script>