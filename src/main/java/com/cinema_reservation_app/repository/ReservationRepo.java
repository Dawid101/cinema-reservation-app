package com.cinema_reservation_app.repository;

import com.cinema_reservation_app.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
}
