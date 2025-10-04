package com.cinema_reservation_app.dto;

import com.cinema_reservation_app.entity.Seat;

import java.util.List;

public record CinemaRoomResp(int roomNumber,
                             List<Seat> seats
                             ) {}
