package com.cinema_reservation_app.dto;

import com.cinema_reservation_app.entity.Reservation;
import com.cinema_reservation_app.entity.ReservationSeat;
import com.cinema_reservation_app.entity.ReservationStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ReservationSummaryResp(
        Long id,
        String movieTitle,
        LocalDateTime movieDateTime,
        List<Seat> seats,
        int cinemaRoomNumber,
        ReservationStatus reservationStatus
) {
    public record Seat(
            int rowNumber,
            int seatNumber
    ) {
        static Seat fromReservationSeat(ReservationSeat reservationSeat) {
            return new Seat(reservationSeat.getSeat().getRowNumber(), reservationSeat.getSeat().getSeatNumber());
        }
        static List<Seat> fromReservation(Reservation reservation){
            return reservation.getSeats().stream().map(Seat::fromReservationSeat).toList();
        }
    }

    public static ReservationSummaryResp fromReservation(Reservation reservation) {
        return ReservationSummaryResp.builder()
                .id(reservation.getId())
                .movieTitle(reservation.getScreening().getMovie().getTitle())
                .movieDateTime(reservation.getScreening().getStartTime())
                .seats(Seat.fromReservation(reservation))
                .cinemaRoomNumber(reservation.getScreening().getCinemaRoom().getNumber())
                .reservationStatus(reservation.getReservationStatus())
                .build();
    }
}
