package dao;

import model.Movie5;
import model.RoomType5;
import model.ScreeningRoom5;
import model.Showtime5;
import model.TimeSlot5;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO cho Showtime5.
 * - Search5:            findUpcomingShowtime(movieId, date)
 * - ScheduleShowtime5:  getScheduleList(), createSchedule(movie, room, timeSlot, date)
 */
public class Showtime5DAO extends DAO {

    private static final String BASE_SELECT =
            "SELECT st.id AS stId, st.showDate, "
          + "       m.id AS mId, m.movieName, m.genre, m.format, m.duration, m.status, m.basePrice, "
          + "       ts.id AS tsId, ts.slotName, ts.startTime, ts.endTime, ts.surcharge AS tsSurcharge, "
          + "       r.id AS rId, r.roomName, r.capacity, "
          + "       rt.id AS rtId, rt.typeName, rt.surcharge AS rtSurcharge "
          + "FROM tblShowtime5 st "
          + "JOIN tblMovie5 m         ON m.id  = st.tblMovie5id "
          + "JOIN tblTimeSlot5 ts     ON ts.id = st.tblTimeSlot5id "
          + "JOIN tblScreeningRoom5 r ON r.id  = st.tblScreeningRoom5id "
          + "JOIN tblRoomType5 rt     ON rt.id = r.tblRoomType5id ";

    private static final String ORDER = " ORDER BY st.showDate, ts.startTime, r.id";

    public Showtime5DAO() {
        super();
    }

    /** Lay danh sach lich chieu sap toi (tu hom nay tro di) cho man hinh quan li lich chieu. */
    public List<Showtime5> getScheduleList() {
        String sql = BASE_SELECT + "WHERE st.showDate >= ?" + ORDER;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            return readList(ps);
        } catch (SQLException e) {
            throw new DataAccessException("Lỗi lấy danh sách lịch chiếu", e);
        }
    }

    /** Lay cac lich chieu sap toi (showDate &gt;= date) cua mot phim. */
    public List<Showtime5> findUpcomingShowtime(int movieId, LocalDate date) {
        String sql = BASE_SELECT + "WHERE st.tblMovie5id = ? AND st.showDate >= ?" + ORDER;
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, movieId);
            ps.setDate(2, Date.valueOf(date));
            return readList(ps);
        } catch (SQLException e) {
            throw new DataAccessException("Lỗi lấy lịch chiếu sắp tới của phim", e);
        }
    }

    /**
     * Tao lich chieu moi. Viec kiem tra va them duoc lam trong MOT cau lenh de tranh trung lich:
     * chi them khi phim dang "Đang chiếu" va phong chua co lich o ngay + khung gio do.
     *
     * @return Showtime5 vua tao (da co id), hoac null neu khong tao duoc
     *         (phong da bi dat, phim khong con chieu...)
     */
    public Showtime5 createSchedule(Movie5 movie, ScreeningRoom5 room, TimeSlot5 timeSlot, LocalDate date) {
        String sql = "INSERT INTO tblShowtime5 (showDate, tblMovie5id, tblTimeSlot5id, tblScreeningRoom5id) "
                + "SELECT ?, ?, ?, ? FROM DUAL "
                + "WHERE EXISTS (SELECT 1 FROM tblMovie5 WHERE id = ? AND status = ?) "
                + "  AND NOT EXISTS (SELECT 1 FROM tblShowtime5 "
                + "                  WHERE showDate = ? AND tblTimeSlot5id = ? AND tblScreeningRoom5id = ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            Date d = Date.valueOf(date);
            ps.setDate(1, d);
            ps.setInt(2, movie.getId());
            ps.setInt(3, timeSlot.getId());
            ps.setInt(4, room.getId());
            ps.setInt(5, movie.getId());
            ps.setString(6, Movie5.STATUS_NOW_SHOWING);
            ps.setDate(7, d);
            ps.setInt(8, timeSlot.getId());
            ps.setInt(9, room.getId());
            if (ps.executeUpdate() == 0) {
                return null;
            }
            try (ResultSet keys = ps.getGeneratedKeys()) {
                int id = keys.next() ? keys.getInt(1) : 0;
                return new Showtime5(id, date, movie, room, timeSlot);
            }
        } catch (java.sql.SQLIntegrityConstraintViolationException dup) {
            return null; // hai request dong thoi cung dat 1 phong/khung gio
        } catch (SQLException e) {
            throw new DataAccessException("Lỗi tạo lịch chiếu", e);
        }
    }

    // ---- helpers ----

    private static List<Showtime5> readList(PreparedStatement ps) throws SQLException {
        List<Showtime5> result = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Movie5 movie = new Movie5();
                movie.setId(rs.getInt("mId"));
                movie.setMovieName(rs.getString("movieName"));
                movie.setGenre(rs.getString("genre"));
                movie.setFormat(rs.getString("format"));
                movie.setDuration(rs.getInt("duration"));
                movie.setStatus(rs.getString("status"));
                movie.setBasePrice(rs.getBigDecimal("basePrice"));

                TimeSlot5 slot = new TimeSlot5(rs.getInt("tsId"), rs.getString("slotName"),
                        rs.getTime("startTime").toLocalTime(), rs.getTime("endTime").toLocalTime(),
                        rs.getBigDecimal("tsSurcharge"));

                RoomType5 type = new RoomType5(rs.getInt("rtId"), rs.getString("typeName"),
                        rs.getBigDecimal("rtSurcharge"));
                ScreeningRoom5 room = new ScreeningRoom5(rs.getInt("rId"), rs.getString("roomName"),
                        rs.getInt("capacity"), type);

                result.add(new Showtime5(rs.getInt("stId"), rs.getDate("showDate").toLocalDate(),
                        movie, room, slot));
            }
        }
        return result;
    }
}
