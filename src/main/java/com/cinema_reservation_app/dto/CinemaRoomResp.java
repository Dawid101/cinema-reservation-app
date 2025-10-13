package com.cinema_reservation_app.dto;

public record CinemaRoomResp(Long id,
                             int number,
                             int totalSeats,
                             int availableSeats
                             ) {}
