package com.cinema_reservation_app.repository;

import com.cinema_reservation_app.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepo extends JpaRepository<Seat, Long> {
}
