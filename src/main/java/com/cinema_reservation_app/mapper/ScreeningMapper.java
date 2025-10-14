package com.cinema_reservation_app.mapper;

import com.cinema_reservation_app.dto.ScreeningSeatResp;
import com.cinema_reservation_app.dto.SeatResp;
import com.cinema_reservation_app.entity.Screening;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ScreeningMapper {

    public ScreeningSeatResp toScreeningSeatResp(Screening screening){
        Long screeningId = screening.getId();
        LocalDateTime startTime = screening.getStartTime();
        Long cinemaRoomId = screening.getCinemaRoom().getId();
        int cinemaRoomNumber = screening.getCinemaRoom().getNumber();
        String movieTitle = screening.getMovie().getTitle();

        List<SeatResp> seatsResp = screening.getCinemaRoom().getSeats()
                .stream()
                .map(seat -> {
                    Long seatRespId = seat.getId();
                    int seatRespNumber = seat.getSeatNumber();
                    int rowNumber = seat.getRowNumber();
                    boolean isAvailable = seat.isAvailable();
                    return new SeatResp(seatRespId,seatRespNumber,rowNumber,isAvailable);
                }).toList();
        return new ScreeningSeatResp(screeningId,startTime,cinemaRoomId,cinemaRoomNumber,movieTitle,seatsResp);
    }
}
