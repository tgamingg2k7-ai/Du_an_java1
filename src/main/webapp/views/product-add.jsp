<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">

<jsp:include page="/Layouts/sidebar.jsp"/>
<jsp:include page="/Layouts/header.jsp"/>

<div class="main">

    <h2 class="page-title">THÊM SẢN PHẨM</h2>

    <div class="form-box">
        <form action="${pageContext.request.contextPath}/product" method="post">

            <div class="form-row">
                <div class="form-group">
                    <label>Mã sản phẩm</label>
                    <input type="text" name="maSanPham" required>
                </div>

                <div class="form-group">
                    <label>Tên sản phẩm</label>
                    <input type="text" name="tenSP" required>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label>Danh mục</label>
                    <select name="maDM" required>
                        <option value="">-- Chọn danh mục --</option>
                        <c:forEach var="dm" items="${listDanhMuc}">
                            <option value="${dm.MaDM}">
                                    ${dm.TenDanhMuc}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Thương hiệu</label>
                    <select name="maTH" required>
                        <option value="">-- Chọn thương hiệu --</option>
                        <c:forEach var="th" items="${listThuongHieu}">
                            <option value="${th.MaTH}">
                                    ${th.TenThuongHieu}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label>Nhà cung cấp</label>
                    <select name="maNCC">
                        <option value="">-- Không chọn --</option>
                        <c:forEach var="ncc" items="${listNhaCungCap}">
                            <option value="${ncc.MaNCC}">
                                    ${ncc.TenNCC}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>Giá bán</label>
                    <input type="number" name="giaBan" min="0" required>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label>Chất liệu</label>
                    <input type="text" name="chatLieu">
                </div>

                <div class="form-group">
                    <label>Giới tính</label>
                    <select name="gioiTinh">
                        <option value="Nam">Nam</option>
                        <option value="Nữ">Nữ</option>
                        <option value="Unisex">Unisex</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label>Trạng thái</label>
                <select name="trangThai">
                    <option value="1">Đang bán</option>
                    <option value="0">Ngừng bán</option>
                </select>
            </div>

            <div class="form-group">
                <label>Mô tả</label>
                <textarea name="moTa" rows="4"></textarea>
            </div>

            <div class="form-action">
                <button type="submit" class="btn-add">Lưu</button>
                <a href="${pageContext.request.contextPath}/product">Quay lại</a>
            </div>

        </form>
    </div>

</div>

<script src="${pageContext.request.contextPath}/assets/main.js"></script>