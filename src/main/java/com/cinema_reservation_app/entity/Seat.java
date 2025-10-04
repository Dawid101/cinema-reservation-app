package com.cinema_reservation_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int number;

    @Column(name = "row_num")
    private int rowNumber;

    @ManyToOne
    @JoinColumn(name = "theater_room_id")
    private CinemaRoom cinemaRoom;

    private boolean isAvailable = true;
}
