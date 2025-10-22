package com.cinema_reservation_app.dto;



import java.util.List;

public record ReservationReq(Long screeningId,
                             List<ReservationSeatReq> seats
                             ) {
}
