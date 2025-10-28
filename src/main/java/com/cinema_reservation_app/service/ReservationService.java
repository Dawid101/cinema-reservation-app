package com.cinema_reservation_app.service;

import com.cinema_reservation_app.dto.ReservationReq;
import com.cinema_reservation_app.dto.ReservationResp;
import com.cinema_reservation_app.dto.ReservationSeatReq;
import com.cinema_reservation_app.entity.*;
import com.cinema_reservation_app.exception.ReservationAlreadyCanceledException;
import com.cinema_reservation_app.exception.ReservationNotFoundException;
import com.cinema_reservation_app.exception.ScreeningNotFoundException;
import com.cinema_reservation_app.exception.SeatUnavailableException;
import com.cinema_reservation_app.mapper.ReservationMapper;
import com.cinema_reservation_app.repository.ReservationRepo;
import com.cinema_reservation_app.repository.ScreeningRepo;
import com.cinema_reservation_app.repository.SeatRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepo reservationRepo;
    private final ReservationMapper reservationMapper;
    private final ScreeningRepo screeningRepo;
    private final SeatRepo seatRepo;

    @Transactional
    public ReservationResp createReservation(ReservationReq req) {
        log.info("Creating reservation for screeningId={}, seatIds={}", req.screeningId(),
                req.seats().stream().map(ReservationSeatReq::seatId).toList());

        List<ReservationSeatReq> reservationSeatsReq = req.seats();
        List<ReservationSeat> reservationSeats = reservationMapper.toReservationSeatList(reservationSeatsReq);
        List<Long> ids = reservationSeats.stream().map(s -> s.getSeat().getId()).toList();
        List<Seat> seats = seatRepo.findAllByIdForUpdate(ids);

        boolean anyUnavailable = seats.stream().anyMatch(seat -> !seat.isAvailable());
        if (anyUnavailable) {
            log.info("Reservation creation canceled");
            throw new SeatUnavailableException("Some selected seats are already reserved");
        }

        Reservation createdReservation = new Reservation();
        createdReservation.setCreatedAt(LocalDateTime.now());
        createdReservation.setReservationStatus(ReservationStatus.PENDING);

        Screening screening = screeningRepo.findById(req.screeningId())
                .orElseThrow(() -> new ScreeningNotFoundException("Screening not found"));
        createdReservation.setScreening(screening);
        reservationSeats.forEach(createdReservation::addSeat);
        seats.forEach(seat -> seat.setAvailable(false));

        reservationRepo.save(createdReservation);
        log.info("Created reservation for screening id={}, seats={}", req.screeningId(), req.seats());
        return reservationMapper.toReservationResp(createdReservation);
    }

    public ReservationResp confirmReservation(Long id) {
        Reservation reservation = findById(id);
        if (reservation.getReservationStatus() == ReservationStatus.CONFIRMED) {
            throw new IllegalStateException("Reservation already confirmed");
        }

        reservation.setReservationStatus(ReservationStatus.CONFIRMED);
        reservationRepo.save(reservation);

        return reservationMapper.toReservationResp(reservation);
    }

    public ReservationResp cancelReservation(Long id) {
        Reservation reservation = findById(id);
        if (reservation.getReservationStatus() != ReservationStatus.CANCELED) {
            reservation.setReservationStatus(ReservationStatus.CANCELED);
            List<Seat> seats = reservation.getSeats().stream().map(ReservationSeat::getSeat).toList();
            seats.forEach(seat -> seat.setAvailable(true));
            reservationRepo.save(reservation);
            log.info("Reservation id {}, canceled", reservation.getId());
            return reservationMapper.toReservationResp(reservation);
        } else {
            throw new ReservationAlreadyCanceledException("Reservation already canceled");
        }
    }

    public Reservation findById(Long id) {
        return reservationRepo.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
    }


}
