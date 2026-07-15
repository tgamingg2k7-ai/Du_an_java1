<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">

<div class="login-page">

    <div class="login-box">

        <img src="${pageContext.request.contextPath}/assets/images/logo.png" class="login-logo">

        <h2>Đăng nhập hệ thống</h2>

        <form action="${pageContext.request.contextPath}/login" method="post">

            <p class="error">${error}</p>

            <input type="text" name="username" placeholder="Tài khoản" required>

            <input type="password" name="password" placeholder="Mật khẩu" required>

            <button type="submit">Đăng nhập</button>

        </form>

        <p class="login-note">Tài khoản: admin / Mật khẩu: 123</p>

    </div>

</div>