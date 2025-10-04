package com.cinema_reservation_app.repository;

import com.cinema_reservation_app.entity.CinemaRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CinemaRoomRepo extends JpaRepository<CinemaRoom, Long> {
    Optional<CinemaRoom> findByNumber(int number);
}
