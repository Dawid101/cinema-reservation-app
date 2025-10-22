package com.cinema_reservation_app.dto;

import com.cinema_reservation_app.entity.TicketType;

import java.math.BigDecimal;

public record ReservationSeatResp(
        Long id,
        int seatNumber,
        int rowNumber,
        TicketType TicketType,
        BigDecimal price
) {
}
