package com.cinema_reservation_app.exception;

public class CinemaRoomNotFoundException extends RuntimeException {
    public CinemaRoomNotFoundException(String message) {
        super(message);
    }
}
