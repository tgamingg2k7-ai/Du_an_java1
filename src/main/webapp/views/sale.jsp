<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">

<jsp:include page="/Layouts/sidebar.jsp"/>
<jsp:include page="/Layouts/header.jsp"/>

<div class="main">

    <h2 class="page-title">BÁN HÀNG TẠI QUẦY</h2>

    <c:if test="${not empty message}">
        <div style="padding: 12px; margin-bottom: 15px; background: #ecfdf5; border: 1px solid #10b981; color: #065f46; border-radius: 8px;">
                ${message}
        </div>
    </c:if>

    <div style="display: grid; grid-template-columns: 1.4fr 1fr; gap: 20px;">

        <!-- DANH SÁCH SẢN PHẨM -->
        <div class="table-box">
            <h3>Danh sách sản phẩm</h3>

            <table>
                <thead>
                <tr>
                    <th>Mã SP</th>
                    <th>Tên sản phẩm</th>
                    <th>Danh mục</th>
                    <th>Giá</th>
                    <th>Tồn</th>
                    <th>SL</th>
                    <th>Thêm</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="p" items="${products}">
                    <tr>
                        <td>${p.maSanPham}</td>
                        <td>${p.tenSP}</td>
                        <td>${p.tenDanhMuc}</td>
                        <td>${p.giaBan} đ</td>
                        <td>${p.tongSoLuong}</td>

                        <td>
                            <form action="${pageContext.request.contextPath}/sale" method="post" style="display:flex; gap:6px;">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="id" value="${p.maSP}">

                                <input type="number"
                                       name="soLuong"
                                       value="1"
                                       min="1"
                                       style="width:70px;">
                        </td>

                        <td>
                            <button type="submit">Thêm</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty products}">
                    <tr>
                        <td colspan="7" style="text-align:center;">
                            Không có sản phẩm nào
                        </td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>

        <!-- GIỎ HÀNG -->
        <div class="table-box">
            <h3>Giỏ hàng</h3>

            <table>
                <thead>
                <tr>
                    <th>Sản phẩm</th>
                    <th>SL</th>
                    <th>Thành tiền</th>
                    <th>Xóa</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="item" items="${cart}">
                    <tr>
                        <td>${item.product.tenSP}</td>
                        <td>${item.soLuong}</td>
                        <td>${item.thanhTien} đ</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/sale" method="post">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="id" value="${item.product.maSP}">
                                <button type="submit">Xóa</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty cart}">
                    <tr>
                        <td colspan="4" style="text-align:center;">
                            Giỏ hàng trống
                        </td>
                    </tr>
                </c:if>
                </tbody>
            </table>

            <div style="margin-top: 20px;">
                <h3>Tổng tiền: ${total} đ</h3>
            </div>

            <form action="${pageContext.request.contextPath}/sale" method="post">
                <input type="hidden" name="action" value="pay">

                <div class="form-group">
                    <label>Khách hàng</label>
                    <input type="text" name="khachHang" placeholder="Khách lẻ">
                </div>

                <div class="form-action">
                    <button type="submit" class="btn-add">
                        Thanh toán
                    </button>
                </div>
            </form>

            <form action="${pageContext.request.contextPath}/sale" method="post" style="margin-top:10px;">
                <input type="hidden" name="action" value="clear">
                <button type="submit" onclick="return confirm('Xóa toàn bộ giỏ hàng?')">
                    Xóa giỏ hàng
                </button>
            </form>
        </div>

    </div>

</div>

<script src="${pageContext.request.contextPath}/assets/main.js"></script>