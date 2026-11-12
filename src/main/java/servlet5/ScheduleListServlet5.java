package servlet5;

import dao.Showtime5DAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

/**
 * ScheduleShowtime5 - man hinh danh sach lich chieu.
 * Goi Showtime5DAO.getScheduleList() va tra ve ScheduleListScreen5.jsp.
 * Neu vua tao lich chieu thanh cong, hien thong bao (flash message luu o session).
 */
@WebServlet("/ScheduleListServlet5")
public class ScheduleListServlet5 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static final String FLASH = "flashMessage5";

    private Showtime5DAO showtimeDAO;

    @Override
    public void init() {
        showtimeDAO = new Showtime5DAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(FLASH) != null) {
            request.setAttribute("message", session.getAttribute(FLASH));
            session.removeAttribute(FLASH);
        }

        request.setAttribute("upcomingScheduleList", showtimeDAO.getScheduleList());
        request.getRequestDispatcher("/view5/manager5/ScheduleListScreen5.jsp").forward(request, response);
    }
}
