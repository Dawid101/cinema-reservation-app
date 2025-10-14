package com.cinema_reservation_app.controller;

import com.cinema_reservation_app.dto.ScreeningSeatResp;
import com.cinema_reservation_app.service.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/screening")
@RequiredArgsConstructor
public class ScreeningController {

    private final ScreeningService screeningService;

    @GetMapping("/{id}/seats")
    public ResponseEntity<ScreeningSeatResp> getSeatsForScreening(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(screeningService.getScreeningById(id));
    }
}
