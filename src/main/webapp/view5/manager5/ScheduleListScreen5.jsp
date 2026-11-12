<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/view5/member5/Common5.jspf" %>
<%-- ScheduleListScreen5.jsp gui yeu cau lay danh sach lich chieu sap toi den ScheduleListServlet5 --%>
<c:if test="${requestScope.upcomingScheduleList == null}"><jsp:forward page="/ScheduleListServlet5"/></c:if>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CineMan - Quản lí lịch chiếu</title>
  <link rel="stylesheet" href="${ctx}/css/cineman.css">
</head>
<body>
<c:set var="pageHeading" value="QUẢN LÍ LỊCH CHIẾU"/>
<%@ include file="/view5/member5/Header5.jspf" %>

<div class="page">
  <div class="page-title">DANH SÁCH LỊCH CHIẾU SẮP TỚI</div>

  <c:if test="${not empty message}"><div class="msg ok"><c:out value="${message}"/></div></c:if>

  <c:choose>
    <c:when test="${empty upcomingScheduleList}">
      <div class="msg info">Chưa có lịch chiếu nào sắp tới.</div>
    </c:when>
    <c:otherwise>
      <table class="data">
        <thead>
          <tr><th>Phim</th><th>Ngày chiếu</th><th>Khung giờ</th><th>Phòng</th><th>Loại phòng</th>
              <th class="right">Sức chứa</th><th>Trạng thái</th></tr>
        </thead>
        <tbody>
          <c:forEach var="s" items="${upcomingScheduleList}">
            <tr>
              <td><c:out value="${s.movie.movieName}"/></td>
              <td>${s.showDate.format(dateFmt)}</td>
              <td>${s.timeSlot.startTime.format(timeFmt)}&ndash;${s.timeSlot.endTime.format(timeFmt)}</td>
              <td><c:out value="${s.screeningRoom.roomName}"/></td>
              <td><c:out value="${s.screeningRoom.roomType.typeName}"/></td>
              <td class="right">${s.screeningRoom.capacity}</td>
              <td><span class="tag">Sắp chiếu</span></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:otherwise>
  </c:choose>

  <%-- btnAddSchedule: goi AddScheduleScreen5.jsp --%>
  <div class="actions">
    <a class="btn" href="${ctx}/view5/manager5/AddScheduleScreen5.jsp">＋ Thêm lịch chiếu</a>
  </div>
</div>
</body>
</html>
