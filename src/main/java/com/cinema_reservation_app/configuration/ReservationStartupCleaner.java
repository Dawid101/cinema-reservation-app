package com.cinema_reservation_app.configuration;

import com.cinema_reservation_app.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationStartupCleaner {

    private final ReservationService reservationService;

    @EventListener(ApplicationReadyEvent.class)
    public void cancelAllPendingReservations(){
        reservationService.cancelAllPendingReservations();
    }
}
