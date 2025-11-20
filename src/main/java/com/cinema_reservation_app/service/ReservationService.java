package com.cinema_reservation_app.service;

import com.cinema_reservation_app.dto.ReservationResp;
import com.cinema_reservation_app.dto.ReservationSeatReq;
import com.cinema_reservation_app.entity.*;
import com.cinema_reservation_app.exception.ReservationNotFoundException;
import com.cinema_reservation_app.exception.ScreeningNotFoundException;
import com.cinema_reservation_app.exception.SeatUnavailableException;
import com.cinema_reservation_app.mapper.ReservationMapper;
import com.cinema_reservation_app.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final ReservationSeatsRepo reservationSeatRepository;
    private final UserRepo userRepo;


    public ReservationResp createReservation(String username, Long screeningId, List<ReservationSeatReq> seatRequests) {
        Reservation createdReservation = create(username, screeningId, seatRequests);
        return reservationMapper.toReservationResp(createdReservation);
    }

    @Transactional
    public Reservation create(String username, Long screeningId, List<ReservationSeatReq> seatRequests) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Screening screening = screeningRepo.findById(screeningId)
                .orElseThrow(() -> new ScreeningNotFoundException("Screening not found"));

        for (ReservationSeatReq seatReq : seatRequests) {
            if (!isSeatAvailable(seatReq.seatId(), screeningId)) {
                throw new SeatUnavailableException("Seat " + seatReq.seatId() + " is not available");
            }
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setScreening(screening);
        reservation.setReservationStatus(ReservationStatus.PENDING);
        reservation.setCreatedAt(LocalDateTime.now());
        reservation = reservationRepo.save(reservation);

        for (ReservationSeatReq seatReq : seatRequests) {
            Seat seat = seatRepo.findById(seatReq.seatId())
                    .orElseThrow(() -> new SeatUnavailableException("Seat not found"));

            ReservationSeat reservationSeat = new ReservationSeat();
            reservationSeat.setSeat(seat);
            reservationSeat.setReservation(reservation);
            reservationSeat.setScreening(screening);
            reservationSeat.setTicketType(seatReq.ticketType());
            reservationSeat.setStatus(ReservationSeatStatus.ACTIVE);
            reservationSeatRepository.save(reservationSeat);
            reservation.getSeats().add(reservationSeat);
        }

        return reservation;
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

    public ReservationResp cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        reservation.setReservationStatus(ReservationStatus.CANCELED);
        reservation.getSeats().forEach(rs -> rs.setStatus(ReservationSeatStatus.CANCELED));
        reservationRepo.save(reservation);
        return reservationMapper.toReservationResp(reservation);
    }

    public Reservation findById(Long id) {
        return reservationRepo.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
    }

    @Transactional
    public void cancelAllPendingReservations() {
        List<Reservation> reservations = reservationRepo.findAllByReservationStatus(ReservationStatus.PENDING);
        if (!reservations.isEmpty()) {
            log.info("Cancel all pending reservations");
            reservations.forEach(r -> {
                r.setReservationStatus(ReservationStatus.CANCELED);
                r.getSeats().forEach(s -> s.getSeat().setAvailable(true));
            });
        }
    }

    public List<ReservationResp> getAllReservation() {
        List<Reservation> all = reservationRepo.findAll();
        return reservationMapper.toReservationRespList(all);
    }

    public ReservationResp findReservationById(Long id) {
        Reservation reservation = findById(id);
        return reservationMapper.toReservationResp(reservation);
    }

    public boolean isSeatAvailable(Long seatId, Long screeningId) {
        return !reservationSeatRepository.existsBySeatIdAndScreeningIdAndStatus(
                seatId,
                screeningId,
                ReservationSeatStatus.ACTIVE
        );
    }


}
