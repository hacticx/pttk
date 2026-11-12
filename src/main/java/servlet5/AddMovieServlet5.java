package servlet5;

import dao.Movie5DAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Movie5;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * ScheduleShowtime5 - ngoai le 8.x: them phim moi khi phim can chieu chua co.
 * doGet: hien form AddMovieScreen5.jsp. doPost: kiem tra, goi Movie5DAO.createMovie(...),
 * hien "Them phim thanh cong" va nut "Dong y" quay lai man hinh tao lich chieu.
 */
@WebServlet("/AddMovieServlet5")
public class AddMovieServlet5 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final List<String> FORMATS = List.of("2D", "3D", "2D/3D");
    private static final List<String> STATUSES =
            List.of(Movie5.STATUS_NOW_SHOWING, Movie5.STATUS_COMING_SOON, Movie5.STATUS_STOPPED);

    private Movie5DAO movieDAO;

    @Override
    public void init() {
        movieDAO = new Movie5DAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        prepare(request);
        forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        prepare(request);

        String name = trim(request.getParameter("movieName"));
        String genre = trim(request.getParameter("genre"));
        String director = trim(request.getParameter("director"));
        String cast = trim(request.getParameter("cast"));
        String description = trim(request.getParameter("description"));
        String format = trim(request.getParameter("format"));
        String status = trim(request.getParameter("status"));

        String error = null;
        int duration = 0;
        BigDecimal basePrice = null;
        try {
            duration = Integer.parseInt(trim(request.getParameter("duration")));
        } catch (NumberFormatException e) {
            duration = 0;
        }
        try {
            basePrice = new BigDecimal(trim(request.getParameter("basePrice")));
        } catch (NumberFormatException e) {
            basePrice = null;
        }

        if (name.isEmpty() || genre.isEmpty()) {
            error = "Vui lòng nhập tên phim và thể loại";
        } else if (name.length() > 255 || genre.length() > 255 || director.length() > 255
                || cast.length() > 255 || description.length() > 255) {
            error = "Các trường văn bản không được dài quá 255 ký tự";
        } else if (duration <= 0) {
            error = "Thời lượng phải là số nguyên dương (phút)";
        } else if (!FORMATS.contains(format)) {
            error = "Vui lòng chọn định dạng phim";
        } else if (basePrice == null || basePrice.signum() < 0 || basePrice.stripTrailingZeros().scale() > 0) {
            error = "Giá cơ bản phải là số tiền hợp lệ (VNĐ, không âm)";
        } else if (!STATUSES.contains(status)) {
            error = "Trạng thái phim không hợp lệ";
        }

        if (error != null) {
            request.setAttribute("error", error);
            forward(request, response);
            return;
        }

        Movie5 created = movieDAO.createMovie(name, genre, duration, director, cast, description,
                format, status, basePrice.setScale(0, java.math.RoundingMode.UNNECESSARY));
        request.setAttribute("createdMovie", created);
        forward(request, response);
    }

    /** Dat danh sach lua chon va URL "quay lai tao lich chieu" (giu lai ngay/khung gio/phong da chon). */
    private void prepare(HttpServletRequest request) {
        request.setAttribute("formats", FORMATS);
        request.setAttribute("statuses", STATUSES);
        request.setAttribute("returnUrl", buildReturnUrl(request));
    }

    private String buildReturnUrl(HttpServletRequest request) {
        String base = request.getContextPath() + "/AddScheduleServlet5";
        LocalDate date = null;
        Integer slotId = null;
        Integer roomId = null;
        try {
            date = LocalDate.parse(trim(request.getParameter("showDate")));
        } catch (DateTimeParseException ignored) {
        }
        try {
            slotId = Integer.valueOf(trim(request.getParameter("timeSlotId")));
        } catch (NumberFormatException ignored) {
        }
        try {
            roomId = Integer.valueOf(trim(request.getParameter("roomId")));
        } catch (NumberFormatException ignored) {
        }
        if (date != null && slotId != null && roomId != null) {
            return base + "?action=selectRoom&showDate=" + date + "&timeSlotId=" + slotId + "&roomId=" + roomId;
        }
        if (date != null && slotId != null) {
            return base + "?action=findRooms&showDate=" + date + "&timeSlotId=" + slotId;
        }
        return base;
    }

    private void forward(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view5/manager5/AddMovieScreen5.jsp").forward(request, response);
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
