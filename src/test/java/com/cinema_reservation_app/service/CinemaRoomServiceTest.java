package com.cinema_reservation_app.service;

import com.cinema_reservation_app.dto.CinemaRoomResp;
import com.cinema_reservation_app.entity.CinemaRoom;
import com.cinema_reservation_app.entity.Seat;
import com.cinema_reservation_app.exception.CinemaRoomAlreadyExistException;
import com.cinema_reservation_app.exception.CinemaRoomNotFoundException;
import com.cinema_reservation_app.mapper.CinemaRoomMapper;
import com.cinema_reservation_app.repository.CinemaRoomRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CinemaRoomServiceTest {

    @Mock
    private CinemaRoomRepo cinemaRoomRepo;

    @Mock
    private CinemaRoomMapper cinemaRoomMapper;

    @InjectMocks
    private CinemaRoomService cinemaRoomService;

    private CinemaRoom cinemaRoom;
    private CinemaRoomResp cinemaRoomResp;

    @BeforeEach
    void setUp() {
        cinemaRoom = new CinemaRoom();
        cinemaRoom.setId(1L);
        cinemaRoom.setNumber(1);
        cinemaRoom.setSeats(List.of(new Seat()));

        cinemaRoomResp = new CinemaRoomResp(1L, 1, 0, 0);
    }

    @Test
    void getCinemaRoomByNumber_WhenExists_ShouldReturnRoom() {
        //given
        when(cinemaRoomRepo.findByNumber(1)).thenReturn(Optional.of(cinemaRoom));
        when(cinemaRoomMapper.toCinemaRoomResp(cinemaRoom)).thenReturn(cinemaRoomResp);

        //when
        CinemaRoomResp result = cinemaRoomService.getCinemaRoomByNumber(1);

        //then
        assertNotNull(result);
        assertEquals(1, result.number());

        verify(cinemaRoomRepo).findByNumber(1);
        verify(cinemaRoomMapper).toCinemaRoomResp(cinemaRoom);
    }

    @Test
    void getCinemaRoomByNumber_WhenNotExists_ShouldThrowException() {
        //given
        when(cinemaRoomRepo.findByNumber(1)).thenReturn(Optional.empty());

        //when & then
        assertThrows(CinemaRoomNotFoundException.class,
                () -> cinemaRoomService.getCinemaRoomByNumber(1));
        verify(cinemaRoomRepo).findByNumber(1);
        verify(cinemaRoomMapper, never()).toCinemaRoomResp(any());
    }

    @Test
    void getCinemaRoomList_ShouldReturnListOfRooms() {
        //given
        List<CinemaRoom> cinemaRooms = List.of(cinemaRoom);
        List<CinemaRoomResp> expectedResponse = List.of(cinemaRoomResp);

        when(cinemaRoomRepo.findAll()).thenReturn(cinemaRooms);
        when(cinemaRoomMapper.toCinemaRoomList(cinemaRooms)).thenReturn(expectedResponse);

        //when
        List<CinemaRoomResp> result = cinemaRoomService.getCinemaRoomList();

        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(cinemaRoomRepo).findAll();
        verify(cinemaRoomMapper).toCinemaRoomList(cinemaRooms);
    }

    @Test
    void createCinemaRoom_WhenNotExists_ShouldCreateRoom() {
        //given
        when(cinemaRoomRepo.findByNumber(1)).thenReturn(Optional.empty());
        when(cinemaRoomRepo.save(cinemaRoom)).thenReturn(cinemaRoom);
        when(cinemaRoomMapper.toCinemaRoomResp(cinemaRoom)).thenReturn(cinemaRoomResp);

        //when
        CinemaRoomResp result = cinemaRoomService.createCinemaRoom(cinemaRoom);

        //then
        assertNotNull(result);
        assertEquals(1, result.id());
        verify(cinemaRoomRepo).save(cinemaRoom);
        verify(cinemaRoomMapper).toCinemaRoomResp(cinemaRoom);
    }

    @Test
    void createCinemaRoom_WhenExists_ShouldThrowException() {
        // given
        when(cinemaRoomRepo.findByNumber(1)).thenReturn(Optional.of(cinemaRoom));

        // when & then
        assertThrows(CinemaRoomAlreadyExistException.class,
                () -> cinemaRoomService.createCinemaRoom(cinemaRoom));
        verify(cinemaRoomRepo, never()).save(any());
    }

    @Test
    void delete_WhenExists_ShouldDeleteRoom() {
        //given
        when(cinemaRoomRepo.existsById(1L)).thenReturn(true);

        //when
        cinemaRoomService.delete(1L);

        //then
        verify(cinemaRoomRepo).deleteById(1L);
    }

    @Test
    void delete_WhenNotExists_ShouldThrowException() {
        //given
        when(cinemaRoomRepo.existsById(1L)).thenReturn(false);

        //when & then
        assertThrows(CinemaRoomNotFoundException.class,
                () -> cinemaRoomService.delete(1L));
        verify(cinemaRoomRepo, never()).deleteById(any());
    }

    @Test
    void update_WhenRoomNotFound_ShouldThrowException() {
        // given
        CinemaRoom updateRequest = new CinemaRoom();

        when(cinemaRoomRepo.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CinemaRoomNotFoundException.class,
                () -> cinemaRoomService.update(1L, updateRequest));

        verify(cinemaRoomRepo).findById(1L);
        verify(cinemaRoomRepo, never()).findByNumber(anyInt());
        verify(cinemaRoomRepo, never()).save(any());

    }

    @Test
    void update_WhenNumberAlreadyExists_ShouldThrowException(){
        //given
        CinemaRoom existingRoomWithSameNumber = new CinemaRoom();
        existingRoomWithSameNumber.setId(2L);
        existingRoomWithSameNumber.setNumber(5);

        CinemaRoom updateRequest = new CinemaRoom();
        updateRequest.setNumber(5);

        when(cinemaRoomRepo.findById(1L)).thenReturn(Optional.of(cinemaRoom));
        when(cinemaRoomRepo.findByNumber(5)).thenReturn(Optional.of(existingRoomWithSameNumber));

        // when & then
        assertThrows(CinemaRoomAlreadyExistException.class,
                () -> cinemaRoomService.update(1L, updateRequest));

        verify(cinemaRoomRepo).findById(1L);
        verify(cinemaRoomRepo).findByNumber(5);
        verify(cinemaRoomRepo, never()).save(any());
    }

    @Test
    void update_WhenValid_ShouldUpdateRoom(){
        // given
        CinemaRoom updateRequest = new CinemaRoom();
        updateRequest.setNumber(5);
        updateRequest.setSeats(new ArrayList<>());

        when(cinemaRoomRepo.findById(1L)).thenReturn(Optional.of(cinemaRoom));
        when(cinemaRoomRepo.findByNumber(5)).thenReturn(Optional.empty());
        when(cinemaRoomRepo.save(cinemaRoom)).thenReturn(cinemaRoom);
        when(cinemaRoomMapper.toCinemaRoomResp(cinemaRoom)).thenReturn(cinemaRoomResp);

        // when
        CinemaRoomResp result = cinemaRoomService.update(1L, updateRequest);

        // then
        assertNotNull(result);
        verify(cinemaRoomRepo).findById(1L);
        verify(cinemaRoomRepo).findByNumber(5);
        verify(cinemaRoomRepo).save(cinemaRoom);

    }

    @Test
    void update_WhenSameNumberForSameRoom_ShouldUpdateSuccessfully() {
        // given
        cinemaRoom.setId(1L);
        cinemaRoom.setNumber(5);

        CinemaRoom updateRequest = new CinemaRoom();
        updateRequest.setNumber(5);
        updateRequest.setSeats(new ArrayList<>());

        when(cinemaRoomRepo.findById(1L)).thenReturn(Optional.of(cinemaRoom));
        when(cinemaRoomRepo.findByNumber(5)).thenReturn(Optional.of(cinemaRoom));
        when(cinemaRoomRepo.save(cinemaRoom)).thenReturn(cinemaRoom);
        when(cinemaRoomMapper.toCinemaRoomResp(cinemaRoom)).thenReturn(cinemaRoomResp);

        // when
        CinemaRoomResp result = cinemaRoomService.update(1L, updateRequest);

        // then
        assertNotNull(result);
        verify(cinemaRoomRepo).save(cinemaRoom);
    }




}