<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="sidebar" id="sidebar">

    <div class="sidebar-logo">
        <img src="${pageContext.request.contextPath}/assets/images/logo.png" alt="Logo">

        <div class="brand-text">
            <h3>TuanTu Shop</h3>
        </div>
    </div>

    <ul class="sidebar-menu">

        <li>
            <a href="${pageContext.request.contextPath}/home">
                <span class="icon">▦</span>
                <span class="menu-text">Tổng quan</span>
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/invoice">
                <span class="icon">▣</span>
                <span class="menu-text">Quản lý hóa đơn</span>
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/sale">
                <span class="icon">🛒</span>
                <span class="menu-text">Bán hàng tại quầy</span>
            </a>
        </li>

        <li class="has-submenu open">
            <a href="javascript:void(0)" class="submenu-toggle">
                <span class="icon">▤</span>
                <span class="menu-text">Quản lý sản phẩm</span>
                <span class="arrow">▼</span>
            </a>

            <ul class="submenu">
                <li>
                    <a href="${pageContext.request.contextPath}/product">
                        Sản phẩm
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/attribute?action=variant">
                        Biến thể
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/attribute">
                        Thuộc tính
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/attribute?action=category">
                        Danh mục
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/attribute?action=brand">
                        Thương hiệu
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/attribute?action=color">
                        Màu sắc
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/attribute?action=size">
                        Kích cỡ
                    </a>
                </li>
            </ul>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/account">
                <span class="icon">👥</span>
                <span class="menu-text">Quản lý tài khoản</span>
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/customer">
                <span class="icon">👤</span>
                <span class="menu-text">Quản lý khách hàng</span>
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/promotion">
                <span class="icon">🏷</span>
                <span class="menu-text">Khuyến mãi</span>
            </a>
        </li>

        <li>
            <a href="${pageContext.request.contextPath}/report">
                <span class="icon">📊</span>
                <span class="menu-text">Thống kê</span>
            </a>
        </li>

    </ul>

</div>