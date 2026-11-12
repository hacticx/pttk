package model;

import java.math.BigDecimal;

/** Lop thuc the SeatType5 (Loai ghe). Duoc tham chieu boi Seat5 -> ScreeningRoom5. */
public class SeatType5 {

    private int id;
    private String typeName;
    private BigDecimal surcharge;

    public SeatType5() {
    }

    public SeatType5(int id, String typeName, BigDecimal surcharge) {
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
