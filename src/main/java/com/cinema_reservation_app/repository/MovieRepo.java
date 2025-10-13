package com.cinema_reservation_app.repository;

import com.cinema_reservation_app.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepo extends JpaRepository<Movie,Long> {
    Optional<Movie> findByTitle(String title);
}
