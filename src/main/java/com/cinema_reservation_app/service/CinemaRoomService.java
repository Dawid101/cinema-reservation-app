package com.cinema_reservation_app.service;

import com.cinema_reservation_app.dto.CinemaRoomResp;
import com.cinema_reservation_app.entity.CinemaRoom;
import com.cinema_reservation_app.exception.CinemaRoomNotFoundException;
import com.cinema_reservation_app.mapper.CinemaRoomMapper;
import com.cinema_reservation_app.repository.CinemaRoomRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaRoomService {

    private final CinemaRoomRepo cinemaRoomRepo;
    private final CinemaRoomMapper cinemaRoomMapper;


    public List<CinemaRoomResp> getCinemaRoomList(){
        List<CinemaRoom> cinemaRooms = cinemaRoomRepo.findAll();
        return cinemaRoomMapper.toCinemaRoomList(cinemaRooms);
    }

    public CinemaRoomResp getCinemaRoomByNumber(int cinemaRoomNumber){
        CinemaRoom cinemaRoom = cinemaRoomRepo.findByNumber(cinemaRoomNumber).orElseThrow(() -> new CinemaRoomNotFoundException("Cinema room not found"));
        return cinemaRoomMapper.toCinemaRoomResp(cinemaRoom);
    }


}
