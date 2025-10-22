package com.cinema_reservation_app.dto;

import com.cinema_reservation_app.entity.ReservationStatus;

import java.math.BigDecimal;
import java.util.List;

public record ReservationResp(Long id,
                              ReservationStatus reservationStatus,
                              Long screeningId,
                              List<ReservationSeatResp> seats,
                              BigDecimal fullPrice) {
}
