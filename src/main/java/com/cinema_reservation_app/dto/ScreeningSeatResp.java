package com.cinema_reservation_app.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ScreeningSeatResp(Long id,
                                LocalDateTime startTime,
                                Long cinemaRoomId,
                                int cinemaRoomNumber,
                                String movieTitle,
                                List<SeatResp> seats) {
}
