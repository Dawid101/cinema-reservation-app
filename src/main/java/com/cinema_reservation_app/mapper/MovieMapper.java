package com.cinema_reservation_app.mapper;

import com.cinema_reservation_app.dto.MovieResp;
import com.cinema_reservation_app.dto.ScreeningResp;
import com.cinema_reservation_app.entity.Movie;
import com.cinema_reservation_app.entity.Seat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class MovieMapper {

    public MovieResp toMovieResp(Movie movie) {
        Long id = movie.getId();
        String title = movie.getTitle();
        String description = movie.getDescription();
        int durationMinutes = (int) movie.getDuration().toMinutes();
        LocalDate releaseDate = movie.getReleaseDate();

        List<ScreeningResp> screenings = movie.getScreenings().stream()
                .map(screening -> {
                    Long screeningId = screening.getId();
                    LocalDateTime startTime = screening.getStartTime();
                    Long cinemaRoomId = screening.getCinemaRoom().getId();
                    int cinemaRoomNumber = screening.getCinemaRoom().getNumber();
                    int availableSeats = (int) screening.getCinemaRoom().getSeats().stream()
                            .filter(Seat::isAvailable).count();
                    return new ScreeningResp(screeningId, startTime, cinemaRoomId, cinemaRoomNumber, availableSeats);
                }).toList();

        return new MovieResp(id, title, description, durationMinutes, releaseDate, screenings);
    }

    public List<MovieResp> toListMovieResp(List<Movie> movies) {
        return movies.stream().map(this::toMovieResp).toList();
    }
}
