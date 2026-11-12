# CineMan – code 2 module Search5 & ScheduleShowtime5

Sinh code theo pha **Thiết kế** của file `15D23DCCN725.docx` (J2EE 3 tầng: JSP → Servlet → DAO → MySQL).
Công nghệ: Java 17, Tomcat 9.1, JSP + JSTL, JDBC, MySQL 8, Maven WAR.

## Chạy thử

1. Tạo CSDL: chạy lần lượt `sql/cineman_schema.sql` rồi `sql/cineman_seed.sql` trong MySQL 8.
2. Sửa `src/main/resources/db.properties` (user/password) hoặc đặt biến môi trường
   `CINEMAN_DB_URL`, `CINEMAN_DB_USER`, `CINEMAN_DB_PASSWORD`.
3. `mvn clean package` → deploy `target/CineMan.war` lên Tomcat 10.1 (hoặc chạy từ IDE).
4. Mở `http://localhost:8080/CineMan/` (tự chuyển tới `MainScreen5.jsp`).

## Cấu trúc (khớp biểu đồ gói: view5 / servlet5 / dao / model)

| Gói | Nội dung |
|---|---|
| `model` | Movie5, Showtime5, TimeSlot5, ScreeningRoom5, RoomType5, Seat5, SeatType5 |
| `dao` | DAO, Movie5DAO, Showtime5DAO, TimeSlot5DAO, ScreeningRoom5DAO |
| `servlet5` | SearchServlet5, MovieDetailServlet5, ScheduleListServlet5, AddScheduleServlet5, AddMovieServlet5 |
| `webapp/view5/member5` | MainScreen5.jsp (+ phần dùng chung) |
| `webapp/view5/customer5` | SearchScreen5, MovieListScreen5, MovieDetailScreen5 |
| `webapp/view5/manager5` | ScheduleListScreen5, AddScheduleScreen5, AddMovieScreen5 |

## Luồng chức năng

- **Search5**: SearchScreen5 → MovieListScreen5 → SearchServlet5 → `Movie5DAO.searchByName` → danh sách;
  chọn phim → MovieDetailScreen5 → MovieDetailServlet5 → `Movie5DAO.findById` + `Showtime5DAO.findUpcomingShowtime`.
- **ScheduleShowtime5**: ScheduleListScreen5 → ScheduleListServlet5 → `getScheduleList`; “Thêm lịch chiếu” →
  AddScheduleScreen5 (A: ngày + khung giờ → B: phòng trống → C: phim đang chiếu → Xác nhận) →
  AddScheduleServlet5 → `createSchedule`; thêm phim mới qua AddMovieScreen5/AddMovieServlet5 → `createMovie`.
- Chưa làm (chỉ hiển thị trên MainScreen5, nút xám): đăng nhập/đăng xuất, đăng kí, mua vé, quản lí phim/phòng, thống kê, xuất thẻ.
  Vì chưa có đăng nhập nên các trang của Manager truy cập trực tiếp, không kiểm tra quyền.

## Điều chỉnh nhỏ so với sơ đồ (để chạy được)

- `DAO.con` để `protected` (lớp con cần dùng); kết nối mở lazy và tự mở lại khi hết hạn.
- `AddMovieServlet5` bổ sung `doPost` (lưu phim không nên dùng GET); `doGet` chỉ hiện form.
- Tên phương thức DAO giữ như sơ đồ (không hậu tố 5): `searchByName`, `findById`, `getNowShowingMovie`, `createMovie`,
  `getScheduleList`, `findUpcomingShowtime`, `createSchedule`, `getTimeSlotList`, `findAvailableRoom`.
- `createSchedule` kiểm tra “phim đang chiếu” và “phòng chưa có lịch cùng ngày + khung giờ” trong **một** câu INSERT…SELECT,
  thêm khóa UNIQUE (showDate, khung giờ, phòng) để chống trùng khi 2 người thao tác đồng thời.
- “Mã phòng” (P01…) là giá trị hiển thị suy ra từ `id` vì sơ đồ lớp không có thuộc tính mã phòng.
- Danh sách lịch chiếu chỉ hiện lịch từ hôm nay trở đi, cột Trạng thái luôn là “Sắp chiếu”.
