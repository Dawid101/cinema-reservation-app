package com.cinema_reservation_app.repository;

import com.cinema_reservation_app.entity.Reservation;
import com.cinema_reservation_app.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByReservationStatus(ReservationStatus reservationStatus);
}
