package com.cinema_reservation_app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cinema_rooms")
public class CinemaRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cinema_room_number", unique = true)
    private int number;

    @OneToMany(mappedBy = "cinemaRoom", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Seat> seats;
}

