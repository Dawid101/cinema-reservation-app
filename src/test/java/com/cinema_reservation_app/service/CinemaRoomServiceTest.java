package com.cinema_reservation_app.service;

import com.cinema_reservation_app.dto.CinemaRoomResp;
import com.cinema_reservation_app.entity.CinemaRoom;
import com.cinema_reservation_app.entity.Seat;
import com.cinema_reservation_app.exception.CinemaRoomAlreadyExistException;
import com.cinema_reservation_app.exception.CinemaRoomNotFoundException;
import com.cinema_reservation_app.mapper.CinemaRoomMapper;
import com.cinema_reservation_app.repository.CinemaRoomRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CinemaRoomServiceTest {

    @Mock
    private CinemaRoomRepo cinemaRoomRepo;

    @Mock
    private CinemaRoomMapper cinemaRoomMapper;

    @InjectMocks
    private CinemaRoomService cinemaRoomService;

    private List<Seat> seats;

    @BeforeEach
    void initializeEmptySeatsList() {
        seats = new ArrayList<>();
    }


    @Test
    void shouldReturnMappedCinemaRoomList() {
        //given
        List<CinemaRoom> cinemaRooms = List.of(new CinemaRoom(1L, 1, seats), new CinemaRoom(2L, 2, seats));
        List<CinemaRoomResp> expectedResponse = List.of(new CinemaRoomResp(1, seats), new CinemaRoomResp(2, seats));

        Mockito.when(cinemaRoomRepo.findAll()).thenReturn(cinemaRooms);
        Mockito.when(cinemaRoomMapper.toCinemaRoomList(cinemaRooms)).thenReturn(expectedResponse);

        //when
        List<CinemaRoomResp> result = cinemaRoomService.getCinemaRoomList();

        //then
        Assertions.assertEquals(expectedResponse, result);
        Mockito.verify(cinemaRoomRepo).findAll();
        Mockito.verify(cinemaRoomMapper).toCinemaRoomList(cinemaRooms);
    }

    @Test
    void shouldReturnMappedCinemaRoomByGivenNumber() {
        //given
        CinemaRoom cinemaRoom = new CinemaRoom(1L, 1, seats);
        CinemaRoomResp cinemaRoomResp = new CinemaRoomResp(1, seats);

        Mockito.when(cinemaRoomRepo.findByNumber(cinemaRoom.getNumber())).thenReturn(Optional.of(cinemaRoom));
        Mockito.when(cinemaRoomMapper.toCinemaRoomResp(cinemaRoom)).thenReturn(cinemaRoomResp);

        //when
        CinemaRoomResp result = cinemaRoomService.getCinemaRoomByNumber(cinemaRoom.getNumber());

        //then
        assertEquals(cinemaRoomResp, result);
    }

    @Test
    void shouldSaveCinemaRoomToDbWhenNumberNotExist() {
        //given
        CinemaRoom cinemaRoom = new CinemaRoom(1L, 1, seats);
        CinemaRoomResp cinemaRoomResp = new CinemaRoomResp(1, seats);

        Mockito.when(cinemaRoomRepo.findByNumber(cinemaRoom.getNumber())).thenReturn(Optional.empty());
        Mockito.when(cinemaRoomRepo.save(cinemaRoom)).thenReturn(cinemaRoom);
        Mockito.when(cinemaRoomMapper.toCinemaRoomResp(cinemaRoom)).thenReturn(cinemaRoomResp);

        //when
        CinemaRoomResp result = cinemaRoomService.createCinemaRoom(cinemaRoom);

        //then
        Mockito.verify(cinemaRoomRepo).findByNumber(cinemaRoom.getNumber());
        Mockito.verify(cinemaRoomRepo).save(cinemaRoom);
        Mockito.verify(cinemaRoomMapper).toCinemaRoomResp(cinemaRoom);
        assertEquals(cinemaRoomResp, result);

    }

    @Test
    void shouldThrowCinemaRoomAlreadyExistException() {
        //given
        CinemaRoom cinemaRoom = new CinemaRoom(1L, 1, seats);
        Mockito.when(cinemaRoomRepo.findByNumber(cinemaRoom.getNumber())).thenReturn(Optional.of(cinemaRoom));

        //when / then
        assertThrows(CinemaRoomAlreadyExistException.class, () -> cinemaRoomService.createCinemaRoom(cinemaRoom));
        Mockito.verify(cinemaRoomRepo, Mockito.never()).save(cinemaRoom);
    }

    @Test
    void shouldDeleteCinemaRoomByGivenId() {
        //given
        long id = 1;
        Mockito.when(cinemaRoomRepo.existsById(id)).thenReturn(true);

        //when
        cinemaRoomService.delete(id);

        //then
        Mockito.verify(cinemaRoomRepo).existsById(id);
        Mockito.verify(cinemaRoomRepo).deleteById(id);
    }

    @Test
    void shouldThrowCinemaRoomNotFoundException() {
        //given
        long id = 5;
        Mockito.when(cinemaRoomRepo.existsById(id)).thenReturn(false);

        //when / then
        assertThrows(CinemaRoomNotFoundException.class, () -> cinemaRoomService.delete(id));

        Mockito.verify(cinemaRoomRepo, Mockito.never()).deleteById(id);
    }

    @Test
    void shouldUpdateExistingCinemaRoom() {
        //given
        long id = 1;
        CinemaRoom cinemaRoom = new CinemaRoom(1L, 1, seats);
        CinemaRoom cinemaRoomReq = new CinemaRoom(1L,2,seats);
        CinemaRoomResp cinemaRoomResp = new CinemaRoomResp(2,seats);

        Mockito.when(cinemaRoomRepo.findById(id)).thenReturn(Optional.of(cinemaRoom));
        Mockito.when(cinemaRoomRepo.findByNumber(cinemaRoomReq.getNumber())).thenReturn(Optional.empty());
        Mockito.when(cinemaRoomRepo.save(cinemaRoom)).thenReturn(cinemaRoom);
        Mockito.when(cinemaRoomMapper.toCinemaRoomResp(cinemaRoom)).thenReturn(cinemaRoomResp);

        //when
        CinemaRoomResp result = cinemaRoomService.update(id,cinemaRoomReq);

        //then
        assertEquals(cinemaRoomReq.getNumber(), result.number());
        Mockito.verify(cinemaRoomRepo).findById(id);
        Mockito.verify(cinemaRoomRepo).save(cinemaRoom);
        Mockito.verify(cinemaRoomMapper).toCinemaRoomResp(cinemaRoom);
    }

    @Test
    void shouldThrowCinemaRoomAlreadyExistExceptionWhenUpdate() {
        //given
        long id = 1;
        CinemaRoom cinemaRoom = new CinemaRoom(id, 1, seats);
        CinemaRoom anotherRoom = new CinemaRoom(2L, 1, seats);

        Mockito.when(cinemaRoomRepo.findById(id)).thenReturn(Optional.of(cinemaRoom));
        Mockito.when(cinemaRoomRepo.findByNumber(cinemaRoom.getNumber())).thenReturn(Optional.of(anotherRoom));

        //when / then
        assertThrows(CinemaRoomAlreadyExistException.class,
                () -> cinemaRoomService.update(id, cinemaRoom));

        Mockito.verify(cinemaRoomRepo, Mockito.never()).save(Mockito.any());
    }
}