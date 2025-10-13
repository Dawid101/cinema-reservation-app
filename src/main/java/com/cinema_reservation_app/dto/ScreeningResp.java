package com.cinema_reservation_app.dto;

import java.time.LocalDateTime;

public record ScreeningResp(
        Long id,
        LocalDateTime startTime,
        Long cinemaRoomId,
        int cinemaRoomNumber,
        int availableSeats
) {
}
