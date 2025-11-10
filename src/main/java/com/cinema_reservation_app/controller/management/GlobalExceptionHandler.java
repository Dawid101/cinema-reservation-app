package com.cinema_reservation_app.controller.management;

import com.cinema_reservation_app.dto.ErrorResp;
import com.cinema_reservation_app.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CinemaRoomNotFoundException.class)
    public ResponseEntity<ErrorResp> handleCinemaRoomNotFoundException(CinemaRoomNotFoundException exception) {
        ErrorResp cinemaRoomNotFound = new ErrorResp("CINEMA ROOM NOT FOUND", exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(cinemaRoomNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CinemaRoomAlreadyExistException.class)
    public ResponseEntity<ErrorResp> handleCinemaRoomAlreadyExistException(CinemaRoomAlreadyExistException exception) {
        ErrorResp errorResp = new ErrorResp("CINEMA ROOM ALREADY EXIST", exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResp, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MovieAlreadyExistException.class)
    public ResponseEntity<ErrorResp> handlerMovieAlreadyExistException(MovieAlreadyExistException exception) {
        ErrorResp errorResp = new ErrorResp("MOVIE ALREADY EXIST", exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResp, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ScreeningNotFoundException.class)
    public ResponseEntity<ErrorResp> handlerScreeningNotFoundException(ScreeningNotFoundException exception) {
        ErrorResp errorResp = new ErrorResp("SCREENING NOT FOUND", exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SeatUnavailableException.class)
    public ResponseEntity<ErrorResp> handlerSeatUnavailableException(SeatUnavailableException exception) {
        ErrorResp errorResp = new ErrorResp("SOME SEATS ARE ALREADY RESERVED", exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResp, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ErrorResp> handlerReservationNotFoundException(ReservationNotFoundException exception) {
        ErrorResp errorResp = new ErrorResp("RESERVATION NOT FOUND", exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReservationAlreadyCanceledException.class)
    public ResponseEntity<ErrorResp> handlerReservationAlreadyCanceledException(ReservationAlreadyCanceledException exception) {
        ErrorResp errorResp = new ErrorResp("RESERVATION ALREADY CANCELED", exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResp, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResp> handlerCategoryNotFoundException(CategoryNotFoundException exception){
        ErrorResp errorResp = new ErrorResp("CATEGORY NOT FOUND", exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResp,HttpStatus.NOT_FOUND);
    }
}
