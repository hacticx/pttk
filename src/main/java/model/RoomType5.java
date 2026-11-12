package model;

import java.math.BigDecimal;

/** Lop thuc the RoomType5 (Loai phong chieu). */
public class RoomType5 {

    private int id;
    private String typeName;
    private BigDecimal surcharge;   // phu phi ap dung cho cac phong thuoc loai nay

    public RoomType5() {
    }

    public RoomType5(int id, String typeName, BigDecimal surcharge) {
        this.id = id;
        this.typeName = typeName;
        this.surcharge = surcharge;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }

    public BigDecimal getSurcharge() { return surcharge; }
    public void setSurcharge(BigDecimal surcharge) { this.surcharge = surcharge; }
}
