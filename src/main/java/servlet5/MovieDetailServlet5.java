package servlet5;

import dao.Movie5DAO;
import dao.Showtime5DAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Movie5;
import model.Showtime5;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Search5 - buoc "xem chi tiet phim".
 * Nhan movieId, goi Movie5DAO.findById(id) va Showtime5DAO.findUpcomingShowtime(movieId, today),
 * tra Movie5 + List&lt;Showtime5&gt; ve MovieDetailScreen5.jsp.
 */
@WebServlet("/MovieDetailServlet5")
public class MovieDetailServlet5 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private Movie5DAO movieDAO;
    private Showtime5DAO showtimeDAO;

    @Override
    public void init() {
        movieDAO = new Movie5DAO();
        showtimeDAO = new Showtime5DAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Movie5 movie = null;
        List<Showtime5> upcomingShowtimes = List.of();
        try {
            int movieId = Integer.parseInt(request.getParameter("movieId"));
            movie = movieDAO.findById(movieId);
            if (movie != null) {
                upcomingShowtimes = showtimeDAO.findUpcomingShowtime(movieId, LocalDate.now());
            }
        } catch (NumberFormatException e) {
            // movieId thieu/sai dinh dang -> coi nhu khong tim thay phim
        }

        request.setAttribute("detailLoaded", Boolean.TRUE);
        request.setAttribute("movie", movie);
        request.setAttribute("upcomingShowtimes", upcomingShowtimes);
        request.setAttribute("keyword", request.getParameter("keyword"));
        request.getRequestDispatcher("/view5/customer5/MovieDetailScreen5.jsp").forward(request, response);
    }
}
