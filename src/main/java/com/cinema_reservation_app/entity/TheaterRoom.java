package com.cinema_reservation_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "theater_rooms")
public class TheaterRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int number;

    @OneToMany(mappedBy = "theaterRoom", cascade = CascadeType.ALL)
    private List<Seat> seats;
}

