package dao;

import model.RoomType5;
import model.ScreeningRoom5;
import model.TimeSlot5;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** DAO cho ScreeningRoom5 (module ScheduleShowtime5). */
public class ScreeningRoom5DAO extends DAO {

    public ScreeningRoom5DAO() {
        super();
    }

    /**
     * Tim cac phong con trong: phong chua co lich chieu nao vao dung ngay va khung gio da chon.
     * Moi phong kem thong tin loai phong (RoomType5) de hien thi ten loai va phu phi.
     */
    public List<ScreeningRoom5> findAvailableRoom(TimeSlot5 timeSlot, LocalDate date) {
        String sql = "SELECT r.id, r.roomName, r.capacity, rt.id AS rtId, rt.typeName, rt.surcharge "
                + "FROM tblScreeningRoom5 r "
                + "JOIN tblRoomType5 rt ON rt.id = r.tblRoomType5id "
                + "WHERE NOT EXISTS (SELECT 1 FROM tblShowtime5 st "
                + "                  WHERE st.tblScreeningRoom5id = r.id "
                + "                    AND st.showDate = ? AND st.tblTimeSlot5id = ?) "
                + "ORDER BY r.id";
        List<ScreeningRoom5> result = new ArrayList<>();
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            ps.setInt(2, timeSlot.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RoomType5 type = new RoomType5(rs.getInt("rtId"), rs.getString("typeName"),
                            rs.getBigDecimal("surcharge"));
                    result.add(new ScreeningRoom5(rs.getInt("id"), rs.getString("roomName"),
                            rs.getInt("capacity"), type));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Lỗi tìm phòng chiếu còn trống", e);
        }
        return result;
    }
}
