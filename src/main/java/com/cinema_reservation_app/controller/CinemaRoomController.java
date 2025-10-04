package com.cinema_reservation_app.controller;

import com.cinema_reservation_app.dto.CinemaRoomResp;
import com.cinema_reservation_app.entity.CinemaRoom;
import com.cinema_reservation_app.service.CinemaRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cinema")
@RequiredArgsConstructor
public class CinemaRoomController {

    private final CinemaRoomService cinemaRoomService;

    @GetMapping
    public ResponseEntity<List<CinemaRoomResp>> getAllCinemaRooms() {
        return new ResponseEntity<>(cinemaRoomService.getCinemaRoomList(), HttpStatus.OK);
    }

    @GetMapping("/room/{number}")
    public ResponseEntity<CinemaRoomResp> getCinemaRoomByNumber(@PathVariable int number){
        return new ResponseEntity<>(cinemaRoomService.getCinemaRoomByNumber(number),HttpStatus.FOUND);
    }

}
