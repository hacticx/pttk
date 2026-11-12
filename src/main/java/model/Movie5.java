package model;

import java.math.BigDecimal;

/**
 * Lop thuc the Movie5 (Phim) - pha thiet ke.
 * Dong goi thong tin mot phim; khong chua logic truy van (viec do Movie5DAO dam nhiem).
 */
public class Movie5 {

    public static final String STATUS_NOW_SHOWING = "Đang chiếu";
    public static final String STATUS_COMING_SOON = "Sắp chiếu";
    public static final String STATUS_STOPPED     = "Ngừng chiếu";

    private int id;
    private String movieName;
    private String genre;
    private String director;
    private String cast;
    private String format;      // 2D / 3D / 2D/3D
    private int duration;       // phut
    private String description;
    private String status;      // Đang chiếu / Sắp chiếu / Ngừng chiếu
    private BigDecimal basePrice;

    public Movie5() {
    }

    public Movie5(int id, String movieName, String genre, String director, String cast,
                  String format, int duration, String description, String status,
                  BigDecimal basePrice) {
        this.id = id;
        this.movieName = movieName;
        this.genre = genre;
        this.director = director;
        this.cast = cast;
        this.format = format;
        this.duration = duration;
        this.description = description;
        this.status = status;
        this.basePrice = basePrice;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMovieName() { return movieName; }
    public void setMovieName(String movieName) { this.movieName = movieName; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getCast() { return cast; }
    public void setCast(String cast) { this.cast = cast; }

    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }
}
