package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Lop thuc the ScreeningRoom5 (Phong chieu).
 * Thuoc tinh {@code seat} (List&lt;Seat5&gt;) dung ten theo so do lop thiet ke;
 * hai module Search5 / ScheduleShowtime5 khong can danh sach ghe nen DAO de rong.
 */
public class ScreeningRoom5 {

    private int id;
    private String roomName;
    private int capacity;
    private List<Seat5> seat = new ArrayList<>();
    private RoomType5 roomType;

    public ScreeningRoom5() {
    }

    public ScreeningRoom5(int id, String roomName, int capacity, RoomType5 roomType) {
        this.id = id;
        this.roomName = roomName;
        this.capacity = capacity;
        this.roomType = roomType;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public List<Seat5> getSeat() { return seat; }
    public void setSeat(List<Seat5> seat) { this.seat = seat; }

    public RoomType5 getRoomType() { return roomType; }
    public void setRoomType(RoomType5 roomType) { this.roomType = roomType; }
}
