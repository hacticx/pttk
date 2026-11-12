package model;

/** Lop thuc the Seat5 (Ghe). Duoc tham chieu boi ScreeningRoom5.seat. */
public class Seat5 {

    private int id;
    private int row;
    private int column;
    private SeatType5 seatType;

    public Seat5() {
    }

    public Seat5(int id, int row, int column, SeatType5 seatType) {
        this.id = id;
        this.row = row;
        this.column = column;
        this.seatType = seatType;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRow() { return row; }
    public void setRow(int row) { this.row = row; }

    public int getColumn() { return column; }
    public void setColumn(int column) { this.column = column; }

    public SeatType5 getSeatType() { return seatType; }
    public void setSeatType(SeatType5 seatType) { this.seatType = seatType; }
}
