package com.cinema_reservation_app.mapper;

import com.cinema_reservation_app.dto.ReservationResp;
import com.cinema_reservation_app.dto.ReservationSeatReq;
import com.cinema_reservation_app.dto.ReservationSeatResp;
import com.cinema_reservation_app.entity.Reservation;
import com.cinema_reservation_app.entity.ReservationSeat;
import com.cinema_reservation_app.entity.ReservationStatus;
import com.cinema_reservation_app.entity.TicketType;
import com.cinema_reservation_app.repository.SeatRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationMapper {

    private final SeatRepo seatRepo;

    public ReservationResp toReservationResp(Reservation reservation) {
        Long id = reservation.getId();
        ReservationStatus status = reservation.getReservationStatus();
        Long screeningId = reservation.getScreening().getId();
        List<ReservationSeatResp> seats = reservation.getSeats().stream()
                .map(seat -> {
                    Long seatRespId = seat.getId();
                    int seatRespNumber = seat.getSeat().getSeatNumber();
                    int rowNumber = seat.getSeat().getRowNumber();
                    TicketType ticketType = seat.getTicketType();
                    BigDecimal price = ticketType.getPrice();
                    return new ReservationSeatResp(seatRespId, seatRespNumber, rowNumber, ticketType, price);
                }).toList();
        BigDecimal fullAmount = seats.stream()
                .map(ReservationSeatResp::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        return new ReservationResp(id, status, screeningId, seats, fullAmount);
    }

    public ReservationSeat toReservationSeat(ReservationSeatReq reservationSeatReq) {
        Long id = reservationSeatReq.id();
        TicketType ticketType = reservationSeatReq.ticketType();
        ReservationSeat reservationSeat = new ReservationSeat();
        reservationSeat.setSeat(seatRepo.getReferenceById(id));
        reservationSeat.setTicketType(ticketType);
        return reservationSeat;
    }
}
