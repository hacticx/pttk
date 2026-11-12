package dao;

import model.TimeSlot5;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** DAO cho TimeSlot5 (module ScheduleShowtime5). */
public class TimeSlot5DAO extends DAO {

    public TimeSlot5DAO() {
        super();
    }

    /** Lay danh sach khung gio, sap theo gio bat dau. */
    public List<TimeSlot5> getTimeSlotList() {
        String sql = "SELECT id, slotName, startTime, endTime, surcharge FROM tblTimeSlot5 ORDER BY startTime";
        List<TimeSlot5> result = new ArrayList<>();
        try (PreparedStatement ps = getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new TimeSlot5(
                        rs.getInt("id"),
                        rs.getString("slotName"),
                        rs.getTime("startTime").toLocalTime(),
                        rs.getTime("endTime").toLocalTime(),
                        rs.getBigDecimal("surcharge")));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Lỗi lấy danh sách khung giờ", e);
        }
        return result;
    }
}
