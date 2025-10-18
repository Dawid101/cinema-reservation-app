package com.cinema_reservation_app.repository;

import com.cinema_reservation_app.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SeatRepo extends JpaRepository<Seat, Long> {
    int countByIdInAndIsAvailableFalse(List<Long> seatIds);
}
