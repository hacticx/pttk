<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/view5/member5/Common5.jspf" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>CineMan - Trang chủ</title>
  <link rel="stylesheet" href="${ctx}/css/cineman.css">
</head>
<body>
<c:set var="pageHeading" value="TRANG CHỦ"/>
<%@ include file="/view5/member5/Header5.jspf" %>

<div class="page">
  <div class="page-title">HỆ THỐNG QUẢN LÍ RẠP CHIẾU PHIM</div>
  <p class="subtitle">Các chức năng có nút hoạt động đã được triển khai; các nút màu xám chỉ hiển thị (chưa triển khai).</p>

  <div class="section-title">Thành viên</div>
  <div class="cards">
    <div class="card"><h3>Đăng nhập</h3><p>Thành viên đăng nhập vào hệ thống.</p>
      <button class="btn disabled" disabled>Đăng nhập</button></div>
    <div class="card"><h3>Đăng xuất</h3><p>Kết thúc phiên làm việc hiện tại.</p>
      <button class="btn disabled" disabled>Đăng xuất</button></div>
  </div>

  <div class="section-title">Khách hàng</div>
  <div class="cards">
    <div class="card"><h3>Đăng kí thành viên</h3><p>Khách hàng tạo tài khoản thành viên.</p>
      <button class="btn disabled" disabled>Đăng kí</button></div>
    <div class="card"><h3>Tìm kiếm phim</h3><p>Nhập tên phim để tìm và xem thông tin chi tiết phim.</p>
      <a class="btn" href="${ctx}/view5/customer5/SearchScreen5.jsp">Tìm kiếm phim</a></div>
    <div class="card"><h3>Mua vé trực tuyến</h3><p>Chọn phim, lịch chiếu, ghế và thanh toán.</p>
      <button class="btn disabled" disabled>Mua vé</button></div>
    <div class="card"><h3>Mua vé tại quầy</h3><p>Mua vé trực tiếp tại quầy.</p>
      <button class="btn disabled" disabled>Mua vé tại quầy</button></div>
  </div>

  <div class="section-title">Nhân viên bán hàng</div>
  <div class="cards">
    <div class="card"><h3>Bán vé tại quầy</h3><p>Chọn lịch chiếu, ghế, thu tiền, lập hóa đơn.</p>
      <button class="btn disabled" disabled>Bán vé</button></div>
    <div class="card"><h3>Xuất thẻ thành viên</h3><p>Cấp thẻ thành viên cho khách hàng.</p>
      <button class="btn disabled" disabled>Xuất thẻ</button></div>
  </div>

  <div class="section-title">Nhân viên quản lí</div>
  <div class="cards">
    <div class="card"><h3>Quản lí lịch chiếu</h3><p>Xem danh sách lịch chiếu sắp tới và thêm lịch chiếu mới.</p>
      <a class="btn" href="${ctx}/view5/manager5/ScheduleListScreen5.jsp">Quản lí lịch chiếu</a></div>
    <div class="card"><h3>Quản lí thông tin phim</h3><p>Thêm, sửa, xóa thông tin phim.</p>
      <button class="btn disabled" disabled>Quản lí phim</button></div>
    <div class="card"><h3>Quản lí phòng chiếu</h3><p>Thêm, sửa, xóa thông tin phòng chiếu.</p>
      <button class="btn disabled" disabled>Quản lí phòng chiếu</button></div>
    <div class="card"><h3>Thống kê phim</h3><p>Số liệu tổng hợp theo phim.</p>
      <button class="btn disabled" disabled>Xem thống kê</button></div>
    <div class="card"><h3>Thống kê khách hàng</h3><p>Số liệu tổng hợp theo khách hàng.</p>
      <button class="btn disabled" disabled>Xem thống kê</button></div>
    <div class="card"><h3>Thống kê doanh thu</h3><p>Doanh thu bán vé theo ngày/tháng/khoảng thời gian.</p>
      <button class="btn disabled" disabled>Xem thống kê</button></div>
  </div>

  <div class="footer">CineMan &middot; 15D23DCCN725</div>
</div>
</body>
</html>
