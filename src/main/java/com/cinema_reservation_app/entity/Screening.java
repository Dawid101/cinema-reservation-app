package com.cinema_reservation_app.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Screening {
    private Long id;
    private LocalDateTime startTime;
    private TheaterRoom theaterRoom;
    private Movie movie;
    private List<ReservedSeat> reservedSeats;
}
