<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css">

<jsp:include page="/Layouts/sidebar.jsp"/>
<jsp:include page="/Layouts/header.jsp"/>

<div class="main">

    <h2 class="page-title">THUỘC TÍNH SẢN PHẨM</h2>

    <div class="tabs">
        <a href="${pageContext.request.contextPath}/attribute?action=color" class="${type == 'color' ? 'active' : ''}">Màu sắc</a>
        <a href="${pageContext.request.contextPath}/attribute?action=size" class="${type == 'size' ? 'active' : ''}">Kích thước</a>
        <a href="${pageContext.request.contextPath}/attribute?action=category" class="${type == 'category' ? 'active' : ''}">Danh mục</a>
        <a href="${pageContext.request.contextPath}/attribute?action=brand" class="${type == 'brand' ? 'active' : ''}">Thương hiệu</a>
        <a href="${pageContext.request.contextPath}/attribute?action=variant" class="${type == 'variant' ? 'active' : ''}">Biến thể</a>
    </div>

    <div class="card form-inline-card">

        <form action="${pageContext.request.contextPath}/attribute" method="post">
            <input type="hidden" name="formAction" value="${editAttribute == null ? 'add' : 'update'}">
            <input type="hidden" name="loai" value="${type}">

            <div class="form-grid">
                <div class="form-group">
                    <label>Mã</label>
                    <input name="ma" value="${editAttribute.ma}" ${editAttribute == null ? '' : 'readonly'} required>
                </div>

                <div class="form-group">
                    <label>Tên</label>
                    <input name="ten" value="${editAttribute.ten}" required>
                </div>

                <div class="form-group">
                    <label>Giá trị / Mã HEX</label>
                    <input name="giaTri" value="${editAttribute.giaTri}" required>
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
                    ${editAttribute == null ? 'Thêm thuộc tính' : 'Cập nhật'}
                </button>
                <a class="btn-cancel" href="${pageContext.request.contextPath}/attribute?action=${type}">Làm mới</a>
            </div>
        </form>

    </div>

    <div class="table-box">
        <table>
            <thead>
            <tr>
                <th>Mã</th>
                <th>Tên</th>
                <th>Giá trị</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="a" items="${attributes}">
                <tr>
                    <td>${a.ma}</td>
                    <td>${a.ten}</td>
                    <td>
                        <c:if test="${type == 'color'}">
                            <span class="color-dot" style="background:${a.giaTri}"></span>
                        </c:if>
                            ${a.giaTri}
                    </td>
                    <td>
                        <span class="status ${a.trangThai == 'Hoạt động' ? 'success' : 'danger'}">
                                ${a.trangThai}
                        </span>
                    </td>
                    <td class="action">
                        <a href="${pageContext.request.contextPath}/attribute?action=edit&type=${type}&ma=${a.ma}">✎</a>
                        <a onclick="return confirm('Xóa thuộc tính này?')"
                           href="${pageContext.request.contextPath}/attribute?action=delete&type=${type}&ma=${a.ma}">🗑</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>

<script src="${pageContext.request.contextPath}/assets/main.js"></script>