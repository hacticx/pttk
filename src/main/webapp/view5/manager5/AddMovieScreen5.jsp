<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/view5/member5/Common5.jspf" %>
<%-- Lan dau vao trang: nho AddMovieServlet5 nap danh sach lua chon va URL quay lai --%>
<c:if test="${requestScope.formats == null}"><jsp:forward page="/AddMovieServlet5"/></c:if>
<c:set var="statusValue" value="${empty param.status ? 'Đang chiếu' : param.status}"/>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CineMan - Thêm phim mới</title>
  <link rel="stylesheet" href="${ctx}/css/cineman.css">
</head>
<body>
<c:set var="pageHeading" value="THÊM PHIM MỚI"/>
<%@ include file="/view5/member5/Header5.jspf" %>

<div class="page">
  <div class="page-title">THÊM PHIM MỚI</div>

  <c:choose>
    <%-- 8.4 - 8.6: them thanh cong -> bam "Dong y" de quay lai man hinh tao lich chieu --%>
    <c:when test="${createdMovie != null}">
      <div class="msg ok">Thêm phim thành công: "<c:out value="${createdMovie.movieName}"/>"</div>
      <div class="actions"><a class="btn" href="${returnUrl}">Đồng ý</a></div>
    </c:when>
    <c:otherwise>
      <c:if test="${not empty error}"><div class="msg err"><c:out value="${error}"/></div></c:if>

      <form method="post" action="${ctx}/AddMovieServlet5">
        <%-- giu lai trang thai man hinh tao lich chieu de quay lai --%>
        <input type="hidden" name="showDate" value="<c:out value='${param.showDate}'/>">
        <input type="hidden" name="timeSlotId" value="<c:out value='${param.timeSlotId}'/>">
        <input type="hidden" name="roomId" value="<c:out value='${param.roomId}'/>">

        <div class="section-title">Thông tin phim</div>
        <div class="grid2">
          <div class="field-row"><label for="movieName">Tên phim *</label>
            <input type="text" id="movieName" name="movieName" maxlength="255" required value="<c:out value='${param.movieName}'/>"></div>
          <div class="field-row"><label for="genre">Thể loại *</label>
            <input type="text" id="genre" name="genre" maxlength="255" required value="<c:out value='${param.genre}'/>"></div>
          <div class="field-row"><label for="duration">Thời lượng (phút) *</label>
            <input type="number" id="duration" name="duration" min="1" step="1" required value="<c:out value='${param.duration}'/>"></div>
          <div class="field-row"><label for="format">Định dạng *</label>
            <select id="format" name="format" required>
              <option value="">-- Chọn --</option>
              <c:forEach var="f" items="${formats}"><option value="${f}" ${param.format == f ? 'selected' : ''}>${f}</option></c:forEach>
            </select></div>
          <div class="field-row"><label for="director">Đạo diễn</label>
            <input type="text" id="director" name="director" maxlength="255" value="<c:out value='${param.director}'/>"></div>
          <div class="field-row"><label for="cast">Diễn viên</label>
            <input type="text" id="cast" name="cast" maxlength="255" value="<c:out value='${param.cast}'/>"></div>
          <div class="field-row"><label for="basePrice">Giá cơ bản (đ) *</label>
            <input type="number" id="basePrice" name="basePrice" min="0" step="1000" required value="<c:out value='${param.basePrice}'/>"></div>
          <div class="field-row"><label for="status">Trạng thái *</label>
            <select id="status" name="status" required>
              <c:forEach var="s" items="${statuses}"><option value="${s}" ${statusValue == s ? 'selected' : ''}>${s}</option></c:forEach>
            </select></div>
        </div>
        <div class="field-row"><label for="description">Mô tả</label>
          <textarea id="description" name="description" rows="3" maxlength="255"><c:out value="${param.description}"/></textarea></div>

        <div class="actions">
          <button type="submit" class="btn" name="btnSave">Lưu phim</button>
          <a class="btn secondary" href="${returnUrl}">Hủy</a>
          <span class="spacer"></span>
          <a class="btn secondary" href="${returnUrl}">← Quay lại tạo lịch</a>
        </div>
      </form>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>
