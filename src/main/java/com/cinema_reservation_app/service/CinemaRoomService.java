package com.cinema_reservation_app.service;

import com.cinema_reservation_app.dto.CinemaRoomResp;
import com.cinema_reservation_app.entity.CinemaRoom;
import com.cinema_reservation_app.exception.CinemaRoomAlreadyExistException;
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


    public List<CinemaRoomResp> getCinemaRoomList() {
        List<CinemaRoom> cinemaRooms = cinemaRoomRepo.findAll();
        return cinemaRoomMapper.toCinemaRoomList(cinemaRooms);
    }

    public CinemaRoomResp getCinemaRoomByNumber(int cinemaRoomNumber) {
        CinemaRoom cinemaRoom = cinemaRoomRepo.findByNumber(cinemaRoomNumber).orElseThrow(() -> new CinemaRoomNotFoundException("Cinema room not found"));
        return cinemaRoomMapper.toCinemaRoomResp(cinemaRoom);
    }

    public CinemaRoomResp save(CinemaRoom cinemaRoom) {
        if (cinemaRoomRepo.findByNumber(cinemaRoom.getNumber()).isEmpty()) {
            CinemaRoom createdCinemaRoom = cinemaRoomRepo.save(cinemaRoom);
            return cinemaRoomMapper.toCinemaRoomResp(createdCinemaRoom);
        } else {
            throw new CinemaRoomAlreadyExistException("Cinema room with number " + cinemaRoom.getNumber() + " already exist.");
        }
    }

    public void delete(Long id) {
        cinemaRoomRepo.deleteById(id);
    }

    public CinemaRoomResp update(Long id, CinemaRoom cinemaRoomReq) {
        CinemaRoom cinemaRoom = cinemaRoomRepo.findById(id).orElseThrow(() -> new CinemaRoomNotFoundException("Cinema room not found"));
        if (cinemaRoomRepo.findByNumber(cinemaRoomReq.getNumber()).isPresent()) {
            throw new CinemaRoomAlreadyExistException("Cinema room with number " + cinemaRoomReq.getNumber() + " already exist.");
        }
        cinemaRoom.setNumber(cinemaRoomReq.getNumber());
        cinemaRoom.setSeats(cinemaRoomReq.getSeats());
        cinemaRoomRepo.save(cinemaRoom);
        return cinemaRoomMapper.toCinemaRoomResp(cinemaRoom);
    }
}
