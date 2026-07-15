<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="header">

    <div class="header-left">
        <button class="menu-btn" type="button" onclick="toggleSidebar()">☰</button>
        <input type="text" class="search-input" placeholder="Tìm kiếm sản phẩm, hóa đơn, khách hàng...">
    </div>

    <div class="header-right">
        <button class="header-icon" type="button">🔔</button>
        <button class="header-icon" type="button" id="darkBtn">🌙</button>

        <div class="admin-wrapper">
            <div class="admin-box" id="userBtn">
                <div class="avatar">A</div>
                <div>
                    <strong>Admin</strong>
                    <span>Quản trị viên</span>
                </div>
            </div>

            <div class="logout-menu" id="logoutMenu">
                <a href="${pageContext.request.contextPath}/account">Tài khoản</a>
                <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </div>
        </div>
    </div>

</div>