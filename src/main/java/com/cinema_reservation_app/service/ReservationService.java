package com.cinema_reservation_app.service;

import com.cinema_reservation_app.dto.ReservationReq;
import com.cinema_reservation_app.dto.ReservationResp;
import com.cinema_reservation_app.entity.Reservation;
import com.cinema_reservation_app.entity.ReservationStatus;
import com.cinema_reservation_app.entity.Screening;
import com.cinema_reservation_app.entity.Seat;
import com.cinema_reservation_app.mapper.ReservationMapper;
import com.cinema_reservation_app.repository.ReservationRepo;
import com.cinema_reservation_app.repository.ScreeningRepo;
import com.cinema_reservation_app.repository.SeatRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepo reservationRepo;
    private final ReservationMapper reservationMapper;
    private final ScreeningRepo screeningRepo;
    private final SeatRepo seatRepo;

    public ReservationResp createReservation(ReservationReq req){

        Reservation createdReservation = new Reservation();
        createdReservation.setCreatedAt(LocalDateTime.now());
        createdReservation.setReservationStatus(ReservationStatus.PENDING);

        Screening screening = screeningRepo.findById(req.screeningId())
                .orElseThrow(() -> new RuntimeException("Screening not found"));
        createdReservation.setScreening(screening);

        List<Seat> seats = seatRepo.findAllById(req.seatIds());
        createdReservation.setSeats(seats);

        createdReservation.setTicketType(req.ticketType());

        reservationRepo.save(createdReservation);
        return reservationMapper.toReservationResp(createdReservation);
    }
}
