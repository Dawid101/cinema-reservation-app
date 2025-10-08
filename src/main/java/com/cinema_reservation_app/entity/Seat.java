package com.cinema_reservation_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Long id;
    private int seatNumber;

    @Column(name = "row_num")
    private int rowNumber;

    @ManyToOne
    @JoinColumn(name = "cinema_room_id")
    @JsonBackReference
    private CinemaRoom cinemaRoom;

    private boolean isAvailable = true;
}
