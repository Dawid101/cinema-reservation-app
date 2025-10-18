package com.cinema_reservation_app.exception;

public class SeatUnavailableException extends RuntimeException {
    public SeatUnavailableException(String s) {
        super(s);
    }
}
