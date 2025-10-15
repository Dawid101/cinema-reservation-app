package com.cinema_reservation_app.mapper;

import com.cinema_reservation_app.dto.ReservationResp;
import com.cinema_reservation_app.dto.SeatResp;
import com.cinema_reservation_app.entity.Reservation;
import com.cinema_reservation_app.entity.ReservationStatus;
import com.cinema_reservation_app.entity.TicketType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationMapper {

    public ReservationResp toReservationResp(Reservation reservation){
        Long id = reservation.getId();
        ReservationStatus status = reservation.getReservationStatus();
        Long screeningId = reservation.getScreening().getId();
        TicketType ticketType = reservation.getTicketType();
        List<SeatResp> seats = reservation.getSeats().stream()
                .map(seat -> {
                    Long seatRespId = seat.getId();
                    int seatRespNumber = seat.getSeatNumber();
                    int rowNumber = seat.getRowNumber();
                    boolean isAvailable = seat.isAvailable();
                    return new SeatResp(seatRespId,seatRespNumber,rowNumber,isAvailable);
                }).toList();
        return new ReservationResp(id,status,screeningId,seats,ticketType);
    }
}
