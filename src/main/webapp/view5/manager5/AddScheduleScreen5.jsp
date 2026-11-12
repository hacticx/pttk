<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/view5/member5/Common5.jspf" %>
<%-- AddScheduleScreen5.jsp lay khung gio / phong trong / phim dang chieu thong qua AddScheduleServlet5 --%>
<c:if test="${requestScope.scheduleLoaded == null}"><jsp:forward page="/AddScheduleServlet5"/></c:if>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CineMan - Tạo lịch chiếu mới</title>
  <link rel="stylesheet" href="${ctx}/css/cineman.css">
</head>
<body>
<c:set var="pageHeading" value="TẠO LỊCH CHIẾU MỚI"/>
<%@ include file="/view5/member5/Header5.jspf" %>

<div class="page">
  <div class="page-title">TẠO LỊCH CHIẾU MỚI</div>

  <c:if test="${not empty error}"><div class="msg err"><c:out value="${error}"/></div></c:if>

  <%-- ===== A. Chon ngay chieu va khung gio ===== --%>
  <div class="section-title">A. Thông tin lịch chiếu</div>
  <form method="get" action="${ctx}/AddScheduleServlet5">
    <input type="hidden" name="action" value="findRooms">
    <div class="grid2">
      <div class="field-row">
        <label for="showDate">Ngày chiếu *</label>
        <input type="date" id="showDate" name="showDate" required min="${today}" value="${showDate}">
      </div>
      <div class="field-row">
        <label for="timeSlotId">Khung giờ *</label>
        <select id="timeSlotId" name="timeSlotId" required>
          <option value="">-- Chọn khung giờ --</option>
          <c:forEach var="t" items="${timeSlots}">
            <option value="${t.id}" ${t.id == selectedTimeSlotId ? 'selected' : ''}>
              <c:out value="${t.slotName}"/> ${t.startTime.format(timeFmt)}&ndash;${t.endTime.format(timeFmt)}
              (+<fmt:formatNumber value="${t.surcharge}" pattern="#,##0"/>đ)
            </option>
          </c:forEach>
        </select>
      </div>
    </div>
    <div class="actions"><button type="submit" class="btn" name="subContinue">Tiếp tục</button></div>
  </form>

  <%-- ===== B. Phong con trong ===== --%>
  <c:if test="${availableRooms != null}">
    <div class="section-title">B. Phòng còn trống trong ngày/khung giờ đã chọn</div>
    <form method="get" action="${ctx}/AddScheduleServlet5">
      <input type="hidden" name="action" value="selectRoom">
      <input type="hidden" name="showDate" value="${showDate}">
      <input type="hidden" name="timeSlotId" value="${selectedTimeSlotId}">
      <table class="data">
        <thead>
          <tr><th>Chọn</th><th>Mã phòng</th><th>Tên phòng</th><th>Loại phòng</th>
              <th class="right">Sức chứa</th><th class="right">Phụ phí loại phòng</th><th>Tình trạng</th></tr>
        </thead>
        <tbody>
          <c:forEach var="r" items="${availableRooms}">
            <tr>
              <td><input type="radio" name="roomId" value="${r.id}" required ${r.id == selectedRoomId ? 'checked' : ''}></td>
              <td>P<fmt:formatNumber value="${r.id}" minIntegerDigits="2"/></td>
              <td><c:out value="${r.roomName}"/></td>
              <td><c:out value="${r.roomType.typeName}"/></td>
              <td class="right">${r.capacity}</td>
              <td class="right"><fmt:formatNumber value="${r.roomType.surcharge}" pattern="#,##0"/> đ</td>
              <td><span class="tag now">Còn trống</span></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      <div class="actions"><button type="submit" class="btn" name="subSelectRoom">Tiếp tục</button></div>
    </form>
  </c:if>

  <%-- ===== C. Chon phim dang chieu ===== --%>
  <c:if test="${nowShowingMovie != null}">
    <div class="section-title">C. Chọn phim đang chiếu</div>
    <form method="post" action="${ctx}/AddScheduleServlet5">
      <input type="hidden" name="showDate" value="${showDate}">
      <input type="hidden" name="timeSlotId" value="${selectedTimeSlotId}">
      <input type="hidden" name="roomId" value="${selectedRoomId}">

      <c:choose>
        <c:when test="${empty nowShowingMovie}">
          <div class="msg info">Hiện chưa có phim nào đang chiếu. Hãy thêm phim mới.</div>
        </c:when>
        <c:otherwise>
          <table class="data">
            <thead>
              <tr><th>Chọn</th><th>Tên phim</th><th>Thể loại</th><th>Thời lượng</th><th>Định dạng</th>
                  <th>Trạng thái</th><th class="right">Giá cơ bản</th></tr>
            </thead>
            <tbody>
              <c:forEach var="m" items="${nowShowingMovie}">
                <tr>
                  <td><input type="radio" name="movieId" value="${m.id}" required ${m.id == selectedMovieId ? 'checked' : ''}></td>
                  <td><c:out value="${m.movieName}"/></td>
                  <td><c:out value="${m.genre}"/></td>
                  <td>${m.duration} phút</td>
                  <td><c:out value="${m.format}"/></td>
                  <td><span class="tag now"><c:out value="${m.status}"/></span></td>
                  <td class="right"><fmt:formatNumber value="${m.basePrice}" pattern="#,##0"/> đ</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:otherwise>
      </c:choose>

      <c:url var="addMovieUrl" value="/view5/manager5/AddMovieScreen5.jsp">
        <c:param name="showDate" value="${showDate}"/>
        <c:param name="timeSlotId" value="${selectedTimeSlotId}"/>
        <c:param name="roomId" value="${selectedRoomId}"/>
      </c:url>
      <div class="actions">
        <a class="btn secondary" href="${addMovieUrl}">＋ Thêm phim mới</a>
        <span class="spacer"></span>
        <button type="submit" class="btn" name="btnConfirm" ${empty nowShowingMovie ? 'disabled' : ''}>Xác nhận →</button>
      </div>
    </form>
  </c:if>

  <div class="actions"><a class="btn secondary" href="${ctx}/view5/manager5/ScheduleListScreen5.jsp">← Quay lại danh sách lịch chiếu</a></div>
</div>
</body>
</html>
