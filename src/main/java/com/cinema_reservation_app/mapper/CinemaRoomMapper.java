package com.cinema_reservation_app.mapper;

import com.cinema_reservation_app.dto.CinemaRoomResp;
import com.cinema_reservation_app.entity.CinemaRoom;
import com.cinema_reservation_app.entity.Seat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CinemaRoomMapper {

    public CinemaRoomResp toCinemaRoomResp(CinemaRoom cinemaRoom) {
        Long id = cinemaRoom.getId();
        int number = cinemaRoom.getNumber();
        int totalSeats = cinemaRoom.getSeats().size();
        int availableSeats = (int) cinemaRoom.getSeats().stream()
                .filter(Seat::isAvailable)
                .count();

        return new CinemaRoomResp(id, number, totalSeats, availableSeats);
    }

    public List<CinemaRoomResp> toCinemaRoomList(List<CinemaRoom> cinemaRooms) {
        return cinemaRooms.stream()
                .map(this::toCinemaRoomResp)
                .toList();
    }
}
