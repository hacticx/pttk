package servlet5;

import dao.Movie5DAO;
import dao.ScreeningRoom5DAO;
import dao.Showtime5DAO;
import dao.TimeSlot5DAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Movie5;
import model.ScreeningRoom5;
import model.Showtime5;
import model.TimeSlot5;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

/**
 * ScheduleShowtime5 - man hinh them lich chieu (mot trang AddScheduleScreen5.jsp gom 3 buoc A/B/C).
 *
 * doGet, tham so {@code action}:
 *   (khong co)  -> A: tai danh sach khung gio
 *   findRooms   -> A + B: them danh sach phong con trong theo showDate + timeSlotId
 *   selectRoom  -> A + B + C: them danh sach phim dang chieu (da chon roomId)
 * doPost: xac nhan -> Showtime5DAO.createSchedule(movie, room, timeSlot, date).
 */
@WebServlet("/AddScheduleServlet5")
public class AddScheduleServlet5 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private TimeSlot5DAO timeSlotDAO;
    private ScreeningRoom5DAO screeningRoomDAO;
    private Movie5DAO movieDAO;
    private Showtime5DAO showtimeDAO;

    @Override
    public void init() {
        timeSlotDAO = new TimeSlot5DAO();
        screeningRoomDAO = new ScreeningRoom5DAO();
        movieDAO = new Movie5DAO();
        showtimeDAO = new Showtime5DAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        int depth = "selectRoom".equals(action) ? 3 : "findRooms".equals(action) ? 2 : 1;
        prepare(request, depth);
        forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Nap lai toan bo trang thai (khung gio, phong con trong, phim dang chieu) va kiem tra hop le
        State st = prepare(request, 3);
        if (st.error != null) {
            forward(request, response);
            return;
        }

        Integer movieId = parseInt(request.getParameter("movieId"));
        request.setAttribute("selectedMovieId", movieId);
        @SuppressWarnings("unchecked")
        List<Movie5> movies = (List<Movie5>) request.getAttribute("nowShowingMovie");
        Optional<Movie5> movie = movies.stream().filter(m -> movieId != null && m.getId() == movieId).findFirst();
        if (movie.isEmpty()) {
            request.setAttribute("error", "Vui lòng chọn một phim trong danh sách phim đang chiếu");
            forward(request, response);
            return;
        }

        Showtime5 created = showtimeDAO.createSchedule(movie.get(), st.room, st.timeSlot, st.date);
        if (created == null) {
            // Phong vua bi dat boi nguoi khac / phim khong con chieu -> nap lai danh sach
            request.removeAttribute("nowShowingMovie");
            request.removeAttribute("selectedRoomId");
            prepare(request, 2);
            request.setAttribute("error",
                    "Không thể tạo lịch chiếu: phòng đã được đặt hoặc phim không còn chiếu. Vui lòng chọn lại.");
            forward(request, response);
            return;
        }

        request.getSession().setAttribute(ScheduleListServlet5.FLASH, "Tạo lịch chiếu thành công");
        response.sendRedirect(request.getContextPath() + "/view5/manager5/ScheduleListScreen5.jsp");
    }

    // ---------------------------------------------------------------------

    /** Ket qua cua viec nap trang thai tu request. */
    private static final class State {
        String error;
        LocalDate date;
        TimeSlot5 timeSlot;
        ScreeningRoom5 room;
    }

    /**
     * Dat cac thuoc tinh cho AddScheduleScreen5.jsp theo do sau buoc (1=A, 2=B, 3=C).
     * Neu du lieu khong hop le thi dat thuoc tinh "error" va dung o buoc hop le truoc do.
     */
    private State prepare(HttpServletRequest request, int depth) {
        State st = new State();
        LocalDate today = LocalDate.now();
        request.setAttribute("today", today);

        // Buoc A: khung gio (luon nap)
        List<TimeSlot5> timeSlots = timeSlotDAO.getTimeSlotList();
        request.setAttribute("timeSlots", timeSlots);
        request.setAttribute("scheduleLoaded", Boolean.TRUE);
        if (depth < 2) {
            return st;
        }

        // Buoc B: ngay chieu + khung gio -> phong con trong
        st.date = parseDate(request.getParameter("showDate"));
        Integer timeSlotId = parseInt(request.getParameter("timeSlotId"));
        request.setAttribute("showDate", st.date);
        request.setAttribute("selectedTimeSlotId", timeSlotId);

        if (st.date == null || timeSlotId == null) {
            return fail(st, request, "Vui lòng chọn ngày chiếu và khung giờ");
        }
        if (st.date.isBefore(today)) {
            return fail(st, request, "Ngày chiếu phải từ hôm nay trở đi");
        }
        st.timeSlot = timeSlots.stream().filter(t -> t.getId() == timeSlotId).findFirst().orElse(null);
        if (st.timeSlot == null) {
            return fail(st, request, "Khung giờ không hợp lệ");
        }

        List<ScreeningRoom5> rooms = screeningRoomDAO.findAvailableRoom(st.timeSlot, st.date);
        if (rooms.isEmpty()) {
            return fail(st, request, "Không có phòng trống, vui lòng chọn khung giờ hoặc ngày khác");
        }
        request.setAttribute("availableRooms", rooms);
        if (depth < 3) {
            return st;
        }

        // Buoc C: phong da chon -> phim dang chieu
        Integer roomId = parseInt(request.getParameter("roomId"));
        request.setAttribute("selectedRoomId", roomId);
        st.room = rooms.stream().filter(r -> roomId != null && r.getId() == roomId).findFirst().orElse(null);
        if (st.room == null) {
            return fail(st, request, "Vui lòng chọn một phòng chiếu còn trống");
        }
        request.setAttribute("nowShowingMovie", movieDAO.getNowShowingMovie());
        return st;
    }

    private static State fail(State st, HttpServletRequest request, String message) {
        st.error = message;
        request.setAttribute("error", message);
        return st;
    }

    private void forward(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view5/manager5/AddScheduleScreen5.jsp").forward(request, response);
    }

    private static Integer parseInt(String s) {
        try {
            return s == null ? null : Integer.valueOf(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static LocalDate parseDate(String s) {
        try {
            return (s == null || s.isBlank()) ? null : LocalDate.parse(s.trim());
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
