-- =====================================================================
-- CineMan - CSDL cho 2 module Search5 va ScheduleShowtime5 (MySQL 8)
-- Theo "So do CSDL" (pha thiet ke); kieu du lieu theo so do lop:
--   cast VARCHAR, slotName VARCHAR, startTime/endTime TIME,
--   basePrice/surcharge DECIMAL(19,0), ...
-- Chi gom cac bang ma 2 module su dung (tblInvoice5, tblMember5... thuoc
-- cac luong khac, chua trien khai).
-- =====================================================================
CREATE DATABASE IF NOT EXISTS cineman5
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE cineman5;

DROP TABLE IF EXISTS tblShowtime5;
DROP TABLE IF EXISTS tblSeat5;
DROP TABLE IF EXISTS tblSeatType5;
DROP TABLE IF EXISTS tblScreeningRoom5;
DROP TABLE IF EXISTS tblRoomType5;
DROP TABLE IF EXISTS tblTimeSlot5;
DROP TABLE IF EXISTS tblMovie5;

CREATE TABLE tblMovie5 (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    movieName   VARCHAR(255)  NOT NULL,
    genre       VARCHAR(255)  NOT NULL,
    director    VARCHAR(255),
    `cast`      VARCHAR(255),
    format      VARCHAR(255)  NOT NULL,              -- 2D / 3D / 2D/3D
    duration    INT           NOT NULL,              -- phut
    description VARCHAR(255),
    status      VARCHAR(255)  NOT NULL,              -- Đang chiếu / Sắp chiếu / Ngừng chiếu
    basePrice   DECIMAL(19,0) NOT NULL,
    CONSTRAINT chk_movie_duration CHECK (duration > 0),
    CONSTRAINT chk_movie_price    CHECK (basePrice >= 0)
) ENGINE=InnoDB;

CREATE TABLE tblTimeSlot5 (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    slotName  VARCHAR(255)  NOT NULL,
    startTime TIME          NOT NULL,
    endTime   TIME          NOT NULL,
    surcharge DECIMAL(19,0) NOT NULL DEFAULT 0
) ENGINE=InnoDB;

CREATE TABLE tblRoomType5 (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    typeName  VARCHAR(255)  NOT NULL,
    surcharge DECIMAL(19,0) NOT NULL DEFAULT 0
) ENGINE=InnoDB;

CREATE TABLE tblScreeningRoom5 (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    roomName       VARCHAR(255) NOT NULL,
    capacity       INT          NOT NULL,
    tblRoomType5id INT          NOT NULL,
    CONSTRAINT fk_room_roomtype FOREIGN KEY (tblRoomType5id) REFERENCES tblRoomType5(id)
) ENGINE=InnoDB;

CREATE TABLE tblSeatType5 (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    typeName  VARCHAR(255)  NOT NULL,
    surcharge DECIMAL(19,0) NOT NULL DEFAULT 0
) ENGINE=InnoDB;

CREATE TABLE tblSeat5 (
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    `row`                INT NOT NULL,
    `column`             INT NOT NULL,
    tblSeatType5id       INT NOT NULL,
    tblScreeningRoom5id  INT NOT NULL,
    CONSTRAINT fk_seat_seattype FOREIGN KEY (tblSeatType5id)      REFERENCES tblSeatType5(id),
    CONSTRAINT fk_seat_room     FOREIGN KEY (tblScreeningRoom5id) REFERENCES tblScreeningRoom5(id),
    CONSTRAINT uq_seat_position UNIQUE (tblScreeningRoom5id, `row`, `column`)
) ENGINE=InnoDB;

CREATE TABLE tblShowtime5 (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    showDate            DATE NOT NULL,
    tblMovie5id         INT  NOT NULL,
    tblTimeSlot5id      INT  NOT NULL,
    tblScreeningRoom5id INT  NOT NULL,
    CONSTRAINT fk_showtime_movie    FOREIGN KEY (tblMovie5id)         REFERENCES tblMovie5(id),
    CONSTRAINT fk_showtime_timeslot FOREIGN KEY (tblTimeSlot5id)      REFERENCES tblTimeSlot5(id),
    CONSTRAINT fk_showtime_room     FOREIGN KEY (tblScreeningRoom5id) REFERENCES tblScreeningRoom5(id),
    -- Rang buoc nghiep vu: cung ngay, mot phong khong co 2 lich chieu trung khung gio
    CONSTRAINT uq_showtime_room_slot UNIQUE (showDate, tblTimeSlot5id, tblScreeningRoom5id)
) ENGINE=InnoDB;
