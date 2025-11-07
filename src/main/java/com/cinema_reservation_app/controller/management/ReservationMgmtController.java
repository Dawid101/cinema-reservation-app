package com.cinema_reservation_app.controller.management;

import com.cinema_reservation_app.dto.ReservationResp;
import com.cinema_reservation_app.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reservation")
@RequiredArgsConstructor
public class ReservationMgmtController {
    private final ReservationService reservationService;

    @GetMapping("/all")
    public ResponseEntity<List<ReservationResp>> getAllReservation(){
        return ResponseEntity.ok(reservationService.getAllReservation());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResp> getReservationById(@PathVariable Long id){
        return ResponseEntity.ok(reservationService.findReservationById(id));
    }
}
