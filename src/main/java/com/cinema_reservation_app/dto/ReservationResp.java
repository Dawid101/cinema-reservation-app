package com.cinema_reservation_app.dto;

import com.cinema_reservation_app.entity.ReservationStatus;
import com.cinema_reservation_app.entity.TicketType;

import java.util.List;

public record ReservationResp(Long id,
                              ReservationStatus reservationStatus,
                              Long screeningId,
                              List<SeatResp> seats,
                              TicketType ticketType) {
}
