package com.cinema_reservation_app.service;

import com.cinema_reservation_app.dto.ReservationResp;
import com.cinema_reservation_app.entity.Reservation;
import com.cinema_reservation_app.entity.ReservationStatus;
import com.cinema_reservation_app.exception.ReservationAlreadyCanceledException;
import com.cinema_reservation_app.mapper.ReservationMapper;
import com.cinema_reservation_app.repository.ReservationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepo reservationRepo;

    @Mock
    private ReservationMapper reservationMapper;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp(){
        reservation = new Reservation();
    }



    @Test
    void confirmReservation_whenPending_shouldBeConfirmed() {
        //given
        reservation.setReservationStatus(ReservationStatus.PENDING);
        ReservationResp reservationResp =
                new ReservationResp(1L, ReservationStatus.CONFIRMED, 1L, null, null);

        when(reservationRepo.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepo.save(reservation)).thenReturn(reservation);
        when(reservationMapper.toReservationResp(reservation)).thenReturn(reservationResp);

        //when
        ReservationResp result = reservationService.confirmReservation(1L);

        //then
        assertNotNull(result);
        assertEquals(ReservationStatus.CONFIRMED, result.reservationStatus());
        verify(reservationRepo).save(reservation);
        verify(reservationMapper).toReservationResp(reservation);
    }

    @Test
    void confirmReservation_whenConfirmed_shouldThrowException(){
        //given
        reservation.setReservationStatus(ReservationStatus.CONFIRMED);

        when(reservationRepo.findById(1L)).thenReturn(Optional.of(reservation));

        //when & then
        assertThrows(IllegalStateException.class,
                () -> reservationService.confirmReservation(1L));

        verify(reservationRepo, never()).save(any());
    }

    @Test
    void cancelReservation_whenPending_shouldBeCanceled(){
        //given
        ReservationResp reservationResp =
                new ReservationResp(1L, ReservationStatus.CANCELED, 1L, null, null);

        when(reservationRepo.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepo.save(reservation)).thenReturn(reservation);
        when(reservationMapper.toReservationResp(reservation)).thenReturn(reservationResp);

        //when
        ReservationResp result = reservationService.cancelReservation(1L);

        //then
        assertNotNull(result);
        assertEquals(ReservationStatus.CANCELED, result.reservationStatus());
        verify(reservationRepo).save(reservation);
        verify(reservationMapper).toReservationResp(reservation);

    }

    @Test
    void cancelReservation_whenCanceled_shouldThrowException(){
        //given
        reservation.setReservationStatus(ReservationStatus.CANCELED);
        when(reservationRepo.findById(1L)).thenReturn(Optional.of(reservation));

        //when & then
        assertThrows(ReservationAlreadyCanceledException.class,
                () -> reservationService.cancelReservation(1L));
        verify(reservationRepo, never()).save(any());

    }


}