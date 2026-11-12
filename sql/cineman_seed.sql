-- Du lieu mau (chay sau cineman_schema.sql). Ngay chieu tinh tuong doi so voi hom nay.
USE cineman5;

INSERT INTO tblTimeSlot5 (id, slotName, startTime, endTime, surcharge) VALUES
 (1, 'Sáng',   '08:00:00', '11:00:00',     0),
 (2, 'Trưa',   '11:00:00', '14:00:00',     0),
 (3, 'Chiều',  '14:00:00', '17:00:00', 10000),
 (4, 'Tối',    '17:00:00', '20:00:00', 30000),
 (5, 'Đêm',    '20:00:00', '23:00:00', 20000),
 (6, 'Khuya',  '23:00:00', '02:00:00', 15000);

INSERT INTO tblRoomType5 (id, typeName, surcharge) VALUES
 (1, 'Tiêu chuẩn', 0),
 (2, 'VIP',        20000),
 (3, 'IMAX',       40000);

INSERT INTO tblScreeningRoom5 (id, roomName, capacity, tblRoomType5id) VALUES
 (1, 'Phòng 01', 120, 1),
 (2, 'Phòng 02',  80, 2),
 (3, 'Phòng 03', 100, 1),
 (4, 'Phòng 04', 150, 3);

INSERT INTO tblSeatType5 (id, typeName, surcharge) VALUES
 (1, 'Thường', 0),
 (2, 'VIP',    15000);

INSERT INTO tblMovie5 (id, movieName, genre, director, `cast`, format, duration, description, status, basePrice) VALUES
 (1, 'Người Nhện: Khởi đầu mới', 'Hành động', 'Destin Daniel Cretton', 'Tom Holland, Zendaya', '2D/3D', 128,
     'Hành trình mới của chàng thanh niên nhện.', 'Đang chiếu', 75000),
 (2, 'Người Sói', 'Kinh dị', 'Đạo diễn minh họa', 'Diễn viên A, Diễn viên B', '2D', 120,
     'Truyền thuyết người sói trở lại trong đêm trăng tròn.', 'Đang chiếu', 70000),
 (3, 'Người Bất Tử', 'Khoa học viễn tưởng', 'Đạo diễn minh họa', 'Diễn viên C, Diễn viên D', '3D', 132,
     'Cuộc chiến giữa những sinh linh không thể chết.', 'Sắp chiếu', 95000),
 (4, 'Hành Trình Phương Nam', 'Phiêu lưu', 'Đạo diễn minh họa', 'Diễn viên E', '2D', 105,
     'Chuyến đi xuyên Việt của nhóm bạn trẻ.', 'Đang chiếu', 80000),
 (5, 'Mùa Hè Cuối Cùng', 'Tâm lý', 'Đạo diễn minh họa', 'Diễn viên F, Diễn viên G', '2D', 98,
     'Câu chuyện tuổi trẻ trước ngày chia tay.', 'Ngừng chiếu', 65000);

-- Lich chieu: (ngay, phim, khung gio, phong)
INSERT INTO tblShowtime5 (showDate, tblMovie5id, tblTimeSlot5id, tblScreeningRoom5id) VALUES
 (DATE_ADD(CURDATE(), INTERVAL 1 DAY), 1, 4, 1),
 (DATE_ADD(CURDATE(), INTERVAL 1 DAY), 1, 5, 2),
 (DATE_ADD(CURDATE(), INTERVAL 2 DAY), 1, 4, 1),
 (DATE_ADD(CURDATE(), INTERVAL 1 DAY), 2, 3, 3),
 (DATE_ADD(CURDATE(), INTERVAL 3 DAY), 2, 5, 3),
 (DATE_ADD(CURDATE(), INTERVAL 2 DAY), 4, 1, 4);
-- Phim 3 (Sap chieu) va phim 5 chua co lich chieu -> dung de thu ngoai le 6.1
