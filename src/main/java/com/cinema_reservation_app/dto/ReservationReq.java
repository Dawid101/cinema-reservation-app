package com.cinema_reservation_app.dto;

import com.cinema_reservation_app.entity.TicketType;

import java.util.List;

public record ReservationReq(Long screeningId,
                             List<Long> seatIds,
                             TicketType ticketType) {
}
