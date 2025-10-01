package com.cinema_reservation_app.entity;

import java.time.Duration;
import java.time.LocalDate;

public class Movie {
    private Long id;
    private String title;
    private String description;
    private Duration duration;
    private Genre genre;
    private LocalDate releaseDate;
}
