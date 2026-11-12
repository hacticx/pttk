<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="/view5/member5/Common5.jspf" %>
<% response.setStatus(500); %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CineMan - Lỗi hệ thống</title>
  <link rel="stylesheet" href="${ctx}/css/cineman.css">
</head>
<body>
<c:set var="pageHeading" value="LỖI HỆ THỐNG"/>
<%@ include file="/view5/member5/Header5.jspf" %>
<div class="page">
  <div class="page-title">ĐÃ XẢY RA LỖI</div>
  <div class="msg err">Hệ thống không xử lý được yêu cầu. Vui lòng kiểm tra kết nối cơ sở dữ liệu (db.properties) rồi thử lại.</div>
  <c:if test="${not empty requestScope['javax.servlet.error.message']}">
    <div class="msg info">Chi tiết: <c:out value="${requestScope['javax.servlet.error.message']}"/></div>
  </c:if>
  <div class="actions"><a class="btn" href="${ctx}/view5/member5/MainScreen5.jsp">Về trang chủ</a></div>
</div>
</body>
</html>
