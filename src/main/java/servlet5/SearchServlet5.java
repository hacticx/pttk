package servlet5;

import dao.Movie5DAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Movie5;

import java.io.IOException;
import java.util.List;

/**
 * Search5 - buoc "tim phim theo ten".
 * MovieListScreen5.jsp gui keyword den servlet nay; servlet goi Movie5DAO.searchByName(keyword)
 * roi tra keyword + List&lt;Movie5&gt; ve MovieListScreen5.jsp.
 */
@WebServlet("/SearchServlet5")
public class SearchServlet5 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private Movie5DAO movieDAO;

    @Override
    public void init() {
        movieDAO = new Movie5DAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String keyword = request.getParameter("keyword");
        keyword = keyword == null ? "" : keyword.trim();

        if (keyword.isEmpty()) {
            // Tu khoa rong: bao loi, quay lai giao dien tim kiem
            request.setAttribute("error", "Vui lòng nhập từ khóa tìm kiếm");
            request.getRequestDispatcher("/view5/customer5/SearchScreen5.jsp").forward(request, response);
            return;
        }

        List<Movie5> movies = movieDAO.searchByName(keyword);
        request.setAttribute("keyword", keyword);
        request.setAttribute("movies", movies);
        request.getRequestDispatcher("/view5/customer5/MovieListScreen5.jsp").forward(request, response);
    }
}
