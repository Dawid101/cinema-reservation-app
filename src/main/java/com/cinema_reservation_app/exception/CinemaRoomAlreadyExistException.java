package com.cinema_reservation_app.exception;

public class CinemaRoomAlreadyExistException extends RuntimeException {
    public CinemaRoomAlreadyExistException(String message) {
        super(message);
    }
}
