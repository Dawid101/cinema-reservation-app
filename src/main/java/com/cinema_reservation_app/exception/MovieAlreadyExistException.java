package com.cinema_reservation_app.exception;

public class MovieAlreadyExistException extends RuntimeException {
    public MovieAlreadyExistException(String message) {
        super(message);
    }
}
