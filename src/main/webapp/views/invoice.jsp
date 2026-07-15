<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/style.css">

<jsp:include page="/Layouts/sidebar.jsp"/>
<jsp:include page="/Layouts/header.jsp"/>

<div class="main">

    <h2 class="page-title">QUẢN LÝ HÓA ĐƠN</h2>

    <div class="toolbar">

        <a class="btn-add"
           href="${pageContext.request.contextPath}/sale">
            + Tạo hóa đơn
        </a>

        <form action="${pageContext.request.contextPath}/invoice"
              method="get">

            <input type="text"
                   name="keyword"
                   placeholder="Tìm kiếm hóa đơn..."
                   value="<c:out value='${keyword}'/>">

            <button type="submit">Tìm</button>
        </form>

    </div>

    <!-- Chi tiết hóa đơn -->
    <c:if test="${not empty selectedInvoice}">

        <div class="detail-box detail-highlight">

            <h3>
                Chi tiết hóa đơn:
                <c:out value="${selectedInvoice.maHoaDon}"/>
            </h3>

            <p>
                <b>Khách hàng:</b>
                <c:out value="${selectedInvoice.tenKhachHang}"/>
            </p>

            <p>
                <b>Nhân viên:</b>
                <c:out value="${selectedInvoice.tenNhanVien}"/>
            </p>

            <p>
                <b>Ngày tạo:</b>
                <fmt:formatDate
                        value="${selectedInvoice.ngayTao}"
                        pattern="dd/MM/yyyy HH:mm"/>
            </p>

            <p>
                <b>Tổng tiền hàng:</b>
                <fmt:formatNumber
                        value="${selectedInvoice.tongTienHang}"
                        type="number"
                        groupingUsed="true"/>
                đ
            </p>

            <p>
                <b>Tiền giảm:</b>
                <fmt:formatNumber
                        value="${selectedInvoice.tienGiam}"
                        type="number"
                        groupingUsed="true"/>
                đ
            </p>

            <p>
                <b>Tổng thanh toán:</b>
                <fmt:formatNumber
                        value="${selectedInvoice.tongThanhToan}"
                        type="number"
                        groupingUsed="true"/>
                đ
            </p>

            <p>
                <b>Hình thức thanh toán:</b>
                <c:out value="${selectedInvoice.hinhThucThanhToan}"/>
            </p>

            <p>
                <b>Trạng thái:</b>
                <c:out value="${selectedInvoice.trangThai}"/>
            </p>

            <p>
                <b>Ghi chú:</b>
                <c:out value="${selectedInvoice.ghiChu}"/>
            </p>

            <a class="btn-cancel"
               href="${pageContext.request.contextPath}/invoice">
                Đóng
            </a>

        </div>

    </c:if>

    <div class="table-box">

        <table>

            <thead>
            <tr>
                <th>Mã HĐ</th>
                <th>Khách hàng</th>
                <th>Nhân viên</th>
                <th>Ngày tạo</th>
                <th>Tổng thanh toán</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
            </tr>
            </thead>

            <tbody>

            <c:choose>

                <c:when test="${not empty invoices}">

                    <c:forEach var="i" items="${invoices}">

                        <tr>

                            <td>
                                <c:out value="${i.maHoaDon}"/>
                            </td>

                            <td>
                                <c:out value="${i.tenKhachHang}"/>
                            </td>

                            <td>
                                <c:out value="${i.tenNhanVien}"/>
                            </td>

                            <td>
                                <fmt:formatDate
                                        value="${i.ngayTao}"
                                        pattern="dd/MM/yyyy HH:mm"/>
                            </td>

                            <td>
                                <fmt:formatNumber
                                        value="${i.tongThanhToan}"
                                        type="number"
                                        groupingUsed="true"/>
                                đ
                            </td>

                            <td>
                                <span class="status
                                    ${i.trangThai == 'Hoàn thành'
                                    ? 'success'
                                    : 'danger'}">

                                    <c:out value="${i.trangThai}"/>

                                </span>
                            </td>

                            <td class="action">

                                <c:url var="detailUrl" value="/invoice">
                                    <c:param name="action" value="detail"/>
                                    <c:param name="maHD" value="${i.maHD}"/>
                                </c:url>

                                <c:url var="deleteUrl" value="/invoice">
                                    <c:param name="action" value="delete"/>
                                    <c:param name="maHD" value="${i.maHD}"/>
                                </c:url>

                                <a href="${detailUrl}"
                                   title="Xem chi tiết">
                                    👁
                                </a>

                                <a href="${deleteUrl}"
                                   title="Xóa hóa đơn"
                                   onclick="return confirm('Bạn có chắc muốn xóa hóa đơn này?')">
                                    🗑
                                </a>

                            </td>

                        </tr>

                    </c:forEach>

                </c:when>

                <c:otherwise>

                    <tr>
                        <td colspan="7"
                            style="text-align:center; padding:20px;">
                            Không có hóa đơn nào
                        </td>
                    </tr>

                </c:otherwise>

            </c:choose>

            </tbody>

        </table>

    </div>

</div>

<script src="${pageContext.request.contextPath}/assets/main.js"></script>