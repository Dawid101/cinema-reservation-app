package com.cinema_reservation_app.mapper;

import com.cinema_reservation_app.dto.CinemaRoomResp;
import com.cinema_reservation_app.entity.CinemaRoom;
import com.cinema_reservation_app.entity.Seat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CinemaRoomMapper {

    public CinemaRoomResp toCinemaRoomResp(CinemaRoom cinemaRoom) {
        int number = cinemaRoom.getNumber();
        List<Seat> seats = cinemaRoom.getSeats();
        return new CinemaRoomResp(number, seats);
    }

    public List<CinemaRoomResp> toCinemaRoomList(List<CinemaRoom> cinemaRooms) {
        return cinemaRooms.stream()
                .map(this::toCinemaRoomResp)
                .toList();
    }
}
