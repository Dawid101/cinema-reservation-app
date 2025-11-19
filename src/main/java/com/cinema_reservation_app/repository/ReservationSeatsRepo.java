package com.cinema_reservation_app.repository;

import com.cinema_reservation_app.entity.ReservationSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationSeatsRepo extends JpaRepository<ReservationSeat, Long> {
    boolean existsBySeatIdAndScreeningId(Long seatId, Long screeningId);
}
