package com.cinema_reservation_app.repository;

import com.cinema_reservation_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
