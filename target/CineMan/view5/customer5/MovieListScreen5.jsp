<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/view5/member5/Common5.jspf" %>
<%-- MovieListScreen5.jsp gui keyword den SearchServlet5; servlet tra keyword + movies roi forward ve trang nay --%>
<c:if test="${empty requestScope.movies}">
  <c:if test="${requestScope.movies == null}"><jsp:forward page="/SearchServlet5"/></c:if>
</c:if>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CineMan - Kết quả tìm kiếm</title>
  <link rel="stylesheet" href="${ctx}/css/cineman.css">
</head>
<body>
<c:set var="pageHeading" value="KẾT QUẢ TÌM KIẾM PHIM"/>
<%@ include file="/view5/member5/Header5.jspf" %>

<div class="page">
  <div class="page-title">Kết quả cho từ khóa: "<c:out value="${keyword}"/>"</div>

  <form method="get" action="${ctx}/view5/customer5/MovieListScreen5.jsp">
    <div class="field-row">
      <label for="keyword">Từ khóa đang tìm:</label>
      <input type="text" id="keyword" name="keyword" maxlength="255" value="<c:out value='${keyword}'/>">
      <button type="submit" class="btn">Sửa tìm kiếm</button>
    </div>
  </form>

  <c:choose>
    <c:when test="${empty movies}">
      <%-- Ngoai le 4: khong co phim nao khop --%>
      <div class="msg err">Không tìm thấy phim phù hợp với từ khóa đã nhập</div>
      <div class="actions"><a class="btn secondary" href="${ctx}/view5/customer5/SearchScreen5.jsp">← Quay lại tìm kiếm</a></div>
    </c:when>
    <c:otherwise>
      <table class="data">
        <thead>
          <tr><th>STT</th><th>Tên phim</th><th>Thể loại</th><th>Thời lượng</th><th>Định dạng</th>
              <th>Trạng thái</th><th class="right">Giá cơ bản</th><th>Thao tác</th></tr>
        </thead>
        <tbody>
          <c:forEach var="m" items="${movies}" varStatus="loop">
            <c:url var="detailUrl" value="/view5/customer5/MovieDetailScreen5.jsp">
              <c:param name="movieId" value="${m.id}"/>
              <c:param name="keyword" value="${keyword}"/>
            </c:url>
            <tr>
              <td>${loop.index + 1}</td>
              <td><c:out value="${m.movieName}"/></td>
              <td><c:out value="${m.genre}"/></td>
              <td>${m.duration} phút</td>
              <td><c:out value="${m.format}"/></td>
              <td><span class="tag ${m.status == 'Đang chiếu' ? 'now' : ''}"><c:out value="${m.status}"/></span></td>
              <td class="right"><fmt:formatNumber value="${m.basePrice}" pattern="#,##0"/> đ</td>
              <td><a class="btn small" href="${detailUrl}">Xem chi tiết →</a></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>
