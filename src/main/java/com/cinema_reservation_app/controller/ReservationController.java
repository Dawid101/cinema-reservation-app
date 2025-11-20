package com.cinema_reservation_app.controller;

import com.cinema_reservation_app.dto.ReservationReq;
import com.cinema_reservation_app.dto.ReservationResp;
import com.cinema_reservation_app.dto.ReservationSummaryResp;
import com.cinema_reservation_app.entity.Reservation;
import com.cinema_reservation_app.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/add")
    public ResponseEntity<ReservationResp> addReservation(@RequestBody ReservationReq req, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.createReservation(authentication.getName(), req.screeningId(), req.seats()));
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<ReservationResp> confirmReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.confirmReservation(id));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ReservationResp> cancelReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.cancelReservation(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ReservationSummaryResp> createReservation(@RequestBody ReservationReq req, Authentication authentication) {
        Reservation reservation = reservationService.create(authentication.getName(), req.screeningId(), req.seats());
        return ResponseEntity.ok(ReservationSummaryResp.fromReservation(reservation));
    }


}
