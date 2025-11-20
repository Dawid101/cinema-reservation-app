package com.cinema_reservation_app.repository;

import com.cinema_reservation_app.entity.ReservationSeat;
import com.cinema_reservation_app.entity.ReservationSeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationSeatsRepo extends JpaRepository<ReservationSeat, Long> {
    boolean existsBySeatIdAndScreeningIdAndStatus(Long seatId, Long screeningId, ReservationSeatStatus status);
}
