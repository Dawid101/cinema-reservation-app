package com.cinema_reservation_app.repository;

import com.cinema_reservation_app.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepo extends JpaRepository<Screening, Long> {
}
