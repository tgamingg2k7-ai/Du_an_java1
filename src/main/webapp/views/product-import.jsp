<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Nhập sản phẩm bằng Excel</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/style.css">
</head>

<body>

<jsp:include page="/Layouts/sidebar.jsp"/>
<jsp:include page="/Layouts/header.jsp"/>

<div style="
        margin-left: 312px;
        padding: 120px 40px 40px;
        min-height: 100vh;
        background: #f7f8fa;
        position: relative;
        z-index: 10;
">

    <h1 style="
            margin: 0 0 25px;
            color: #222;
            font-size: 34px;
    ">
        NHẬP SẢN PHẨM BẰNG EXCEL
    </h1>

    <div style="
            max-width: 900px;
            padding: 30px;
            border: 1px solid #ddd;
            border-radius: 15px;
            background: white;
    ">

        <h2>Yêu cầu file Excel</h2>

        <p>File phải có định dạng .xlsx hoặc .xls</p>

        <ol>
            <li>Mã sản phẩm</li>
            <li>Tên sản phẩm</li>
            <li>Danh mục</li>
            <li>Thương hiệu</li>
            <li>Giá</li>
            <li>Số lượng</li>
            <li>Trạng thái</li>
        </ol>

        <form action="${pageContext.request.contextPath}/product-import"
              method="post"
              enctype="multipart/form-data">

            <label for="excelFile">
                <strong>Chọn file Excel</strong>
            </label>

            <br><br>

            <input type="file"
                   id="excelFile"
                   name="excelFile"
                   accept=".xlsx,.xls"
                   required>

            <br><br>

            <button type="submit"
                    style="
                        padding: 12px 20px;
                        border: none;
                        border-radius: 8px;
                        background: #ef2b2d;
                        color: white;
                        font-weight: bold;
                        cursor: pointer;
                    ">
                Nhập dữ liệu
            </button>

            <a href="${pageContext.request.contextPath}/product"
               style="
                    display: inline-block;
                    margin-left: 10px;
                    padding: 11px 20px;
                    border-radius: 8px;
                    background: #e5e7eb;
                    color: #222;
                    text-decoration: none;
                    font-weight: bold;
               ">
                Quay lại
            </a>

        </form>

    </div>

</div>

<script src="${pageContext.request.contextPath}/assets/main.js"></script>

</body>
</html>