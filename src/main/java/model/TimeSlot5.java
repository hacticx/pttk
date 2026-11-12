package model;

import java.math.BigDecimal;
import java.time.LocalTime;

/** Lop thuc the TimeSlot5 (Khung gio chieu). */
public class TimeSlot5 {

    private int id;
    private String slotName;
    private LocalTime startTime;
    private LocalTime endTime;
    private BigDecimal surcharge;   // phu phi khung gio

    public TimeSlot5() {
    }

    public TimeSlot5(int id, String slotName, LocalTime startTime, LocalTime endTime, BigDecimal surcharge) {
        this.id = id;
        this.slotName = slotName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.surcharge = surcharge;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSlotName() { return slotName; }
    public void setSlotName(String slotName) { this.slotName = slotName; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public BigDecimal getSurcharge() { return surcharge; }
    public void setSurcharge(BigDecimal surcharge) { this.surcharge = surcharge; }
}
