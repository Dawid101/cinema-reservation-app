package com.cinema_reservation_app.exception;

public class ReservationAlreadyCanceledException extends RuntimeException {
    public ReservationAlreadyCanceledException(String message) {
        super(message);
    }
}
