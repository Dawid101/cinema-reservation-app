package com.cinema_reservation_app.controller;

import com.cinema_reservation_app.dto.ReservationReq;
import com.cinema_reservation_app.dto.ReservationResp;
import com.cinema_reservation_app.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/add")
    public ResponseEntity<ReservationResp> addReservation(@RequestBody ReservationReq req){
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.createReservation(req));
    }
}
