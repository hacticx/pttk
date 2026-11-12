package model;

import java.time.LocalDate;

/** Lop thuc the Showtime5 (Lich chieu): 1 phim, 1 phong, 1 khung gio, 1 ngay chieu. */
public class Showtime5 {

    private int id;
    private LocalDate showDate;
    private Movie5 movie;
    private ScreeningRoom5 screeningRoom;
    private TimeSlot5 timeSlot;

    public Showtime5() {
    }

    public Showtime5(int id, LocalDate showDate, Movie5 movie, ScreeningRoom5 screeningRoom, TimeSlot5 timeSlot) {
        this.id = id;
        this.showDate = showDate;
        this.movie = movie;
        this.screeningRoom = screeningRoom;
        this.timeSlot = timeSlot;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getShowDate() { return showDate; }
    public void setShowDate(LocalDate showDate) { this.showDate = showDate; }

    public Movie5 getMovie() { return movie; }
    public void setMovie(Movie5 movie) { this.movie = movie; }

    public ScreeningRoom5 getScreeningRoom() { return screeningRoom; }
    public void setScreeningRoom(ScreeningRoom5 screeningRoom) { this.screeningRoom = screeningRoom; }

    public TimeSlot5 getTimeSlot() { return timeSlot; }
    public void setTimeSlot(TimeSlot5 timeSlot) { this.timeSlot = timeSlot; }
}
