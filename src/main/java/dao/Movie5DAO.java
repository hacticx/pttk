package dao;

import model.Movie5;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO cho Movie5.
 * - Search5:            searchByName(keyword), findById(id)
 * - ScheduleShowtime5:  getNowShowingMovie(), createMovie(...)
 */
public class Movie5DAO extends DAO {

    private static final String COLUMNS =
            "id, movieName, genre, director, `cast`, format, duration, description, status, basePrice";

    public Movie5DAO() {
        super();
    }

    /** Tim cac phim co ten chua tu khoa (khong phan biet hoa/thuong theo collation utf8mb4_unicode_ci). */
    public List<Movie5> searchByName(String keyword) {
        String sql = "SELECT " + COLUMNS + " FROM tblMovie5 WHERE movieName LIKE ? ESCAPE '\\\\' ORDER BY movieName";
        List<Movie5> result = new ArrayList<>();
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, "%" + escapeLike(keyword) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapMovie(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Lỗi tìm kiếm phim theo tên", e);
        }
        return result;
    }

    /** Lay thong tin chi tiet cua mot phim; null neu khong ton tai. */
    public Movie5 findById(int id) {
        String sql = "SELECT " + COLUMNS + " FROM tblMovie5 WHERE id = ?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapMovie(rs) : null;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Lỗi lấy thông tin phim", e);
        }
    }

    /** Lay danh sach phim co trang thai "Đang chiếu". */
    public List<Movie5> getNowShowingMovie() {
        String sql = "SELECT " + COLUMNS + " FROM tblMovie5 WHERE status = ? ORDER BY movieName";
        List<Movie5> result = new ArrayList<>();
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, Movie5.STATUS_NOW_SHOWING);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapMovie(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Lỗi lấy danh sách phim đang chiếu", e);
        }
        return result;
    }

    /**
     * Them phim moi (thay cho addMovie o pha phan tich).
     *
     * @return Movie5 vua tao (da co id)
     */
    public Movie5 createMovie(String name, String genre, int duration, String director, String cast,
                              String description, String format, String status, BigDecimal basePrice) {
        String sql = "INSERT INTO tblMovie5 (movieName, genre, director, `cast`, format, duration, "
                + "description, status, basePrice) VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, genre);
            ps.setString(3, director);
            ps.setString(4, cast);
            ps.setString(5, format);
            ps.setInt(6, duration);
            ps.setString(7, description);
            ps.setString(8, status);
            ps.setBigDecimal(9, basePrice);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                int id = keys.next() ? keys.getInt(1) : 0;
                return new Movie5(id, name, genre, director, cast, format, duration, description, status, basePrice);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Lỗi thêm phim mới", e);
        }
    }

    // ---- helpers ----

    private static Movie5 mapMovie(ResultSet rs) throws SQLException {
        return new Movie5(
                rs.getInt("id"),
                rs.getString("movieName"),
                rs.getString("genre"),
                rs.getString("director"),
                rs.getString("cast"),
                rs.getString("format"),
                rs.getInt("duration"),
                rs.getString("description"),
                rs.getString("status"),
                rs.getBigDecimal("basePrice"));
    }

    private static String escapeLike(String s) {
        return s.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
    }
}
