package com.cinema_reservation_app.dto;

import com.cinema_reservation_app.entity.TicketType;

public record ReservationSeatReq(Long id,
                                 TicketType ticketType) {
}
