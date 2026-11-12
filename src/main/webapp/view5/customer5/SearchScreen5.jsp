<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/view5/member5/Common5.jspf" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CineMan - Tìm kiếm phim</title>
  <link rel="stylesheet" href="${ctx}/css/cineman.css">
</head>
<body>
<c:set var="pageHeading" value="TÌM KIẾM PHIM"/>
<%@ include file="/view5/member5/Header5.jspf" %>

<div class="page">
  <div class="page-title">TÌM KIẾM THÔNG TIN PHIM</div>
  <p class="subtitle">Nhập tên hoặc một phần tên phim để tìm kiếm.</p>

  <c:if test="${not empty error}"><div class="msg err"><c:out value="${error}"/></div></c:if>

  <%-- keyword + btnSearch (submit): gui keyword sang MovieListScreen5.jsp --%>
  <form method="get" action="${ctx}/view5/customer5/MovieListScreen5.jsp">
    <div class="field-row">
      <label for="keyword">Tên phim / từ khóa:</label>
      <input type="text" id="keyword" name="keyword" placeholder="Nhập từ khóa tìm phim..." maxlength="255" autofocus
             value="<c:out value='${param.keyword}'/>">
      <button type="submit" class="btn" name="btnSearch">Tìm kiếm</button>
    </div>
  </form>
</div>
</body>
</html>
