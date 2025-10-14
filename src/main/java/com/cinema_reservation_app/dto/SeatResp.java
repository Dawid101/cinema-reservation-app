package com.cinema_reservation_app.dto;

public record SeatResp(Long id,
                       int seatNumber,
                       int rowNumber,
                       boolean available) {
}
