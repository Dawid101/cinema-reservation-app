package com.cinema_reservation_app.exception;

public class ScreeningNotFoundException extends RuntimeException {
    public ScreeningNotFoundException(String message) {
        super(message);
    }
}
