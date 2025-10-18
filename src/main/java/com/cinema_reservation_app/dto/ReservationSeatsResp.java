package com.cinema_reservation_app.dto;

public record ReservationSeatsResp(
        Long id,
        int seatNumber,
        int rowNumber
) {
}
