package com.cinema_reservation_app.repository;

import com.cinema_reservation_app.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String categoryName);
}
