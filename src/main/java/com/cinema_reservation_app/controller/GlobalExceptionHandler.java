package com.cinema_reservation_app.controller;

import com.cinema_reservation_app.dto.ErrorResp;
import com.cinema_reservation_app.exception.CinemaRoomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CinemaRoomNotFoundException.class)
    public ResponseEntity<ErrorResp> handleCinemaRoomNotFoundException(CinemaRoomNotFoundException exception){
        ErrorResp cinemaRoomNotFound = new ErrorResp("CINEMA ROOM NOT FOUND", exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(cinemaRoomNotFound, HttpStatus.NOT_FOUND);
    }


}
