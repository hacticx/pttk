<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/view5/member5/Common5.jspf" %>
<%-- MovieDetailScreen5.jsp nhan movieId va goi MovieDetailServlet5 de lay du lieu chi tiet --%>
<c:if test="${requestScope.detailLoaded == null}"><jsp:forward page="/MovieDetailServlet5"/></c:if>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CineMan - Chi tiết phim</title>
  <link rel="stylesheet" href="${ctx}/css/cineman.css">
</head>
<body>
<c:set var="pageHeading" value="CHI TIẾT PHIM"/>
<%@ include file="/view5/member5/Header5.jspf" %>

<div class="page">
  <c:choose>
    <c:when test="${movie == null}">
      <div class="page-title">KHÔNG TÌM THẤY PHIM</div>
      <div class="msg err">Phim được chọn không tồn tại.</div>
    </c:when>
    <c:otherwise>
      <div class="page-title">THÔNG TIN PHIM</div>
      <table class="data detail">
        <tr><th>Tên phim</th><td colspan="3"><strong><c:out value="${movie.movieName}"/></strong></td></tr>
        <tr><th>Thể loại</th><td><c:out value="${movie.genre}"/></td>
            <th>Thời lượng</th><td>${movie.duration} phút</td></tr>
        <tr><th>Đạo diễn</th><td><c:out value="${movie.director}"/></td>
            <th>Định dạng</th><td><c:out value="${movie.format}"/></td></tr>
        <tr><th>Diễn viên</th><td colspan="3"><c:out value="${movie.cast}"/></td></tr>
        <tr><th>Mô tả</th><td colspan="3"><c:out value="${movie.description}"/></td></tr>
        <tr><th>Trạng thái</th><td><span class="tag ${movie.status == 'Đang chiếu' ? 'now' : ''}"><c:out value="${movie.status}"/></span></td>
            <th>Giá cơ bản</th><td><strong><fmt:formatNumber value="${movie.basePrice}" pattern="#,##0"/> đ</strong></td></tr>
      </table>

      <div class="section-title">LỊCH CHIẾU SẮP TỚI</div>
      <c:choose>
        <c:when test="${empty upcomingShowtimes}">
          <%-- Ngoai le 6.1 --%>
          <div class="msg info">Phim hiện chưa có lịch chiếu sắp tới</div>
        </c:when>
        <c:otherwise>
          <table class="data">
            <thead><tr><th>Ngày chiếu</th><th>Khung giờ</th><th>Bắt đầu</th><th>Kết thúc</th><th>Phòng chiếu</th></tr></thead>
            <tbody>
              <c:forEach var="s" items="${upcomingShowtimes}">
                <tr>
                  <td>${s.showDate.format(dateFmt)}</td>
                  <td><c:out value="${s.timeSlot.slotName}"/></td>
                  <td>${s.timeSlot.startTime.format(timeFmt)}</td>
                  <td>${s.timeSlot.endTime.format(timeFmt)}</td>
                  <td><c:out value="${s.screeningRoom.roomName}"/></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:otherwise>
      </c:choose>
    </c:otherwise>
  </c:choose>

  <%-- btnBack (submit): quay lai ket qua tim kiem --%>
  <c:choose>
    <c:when test="${not empty keyword}">
      <form method="get" action="${ctx}/view5/customer5/MovieListScreen5.jsp" class="actions">
        <input type="hidden" name="keyword" value="<c:out value='${keyword}'/>">
        <button type="submit" class="btn secondary" name="btnBack">← Quay lại kết quả</button>
      </form>
    </c:when>
    <c:otherwise>
      <div class="actions"><a class="btn secondary" href="${ctx}/view5/customer5/SearchScreen5.jsp">← Quay lại tìm kiếm</a></div>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>
