package com.cinema_reservation_app.service;

import com.cinema_reservation_app.dto.MovieResp;
import com.cinema_reservation_app.entity.Movie;
import com.cinema_reservation_app.entity.Screening;
import com.cinema_reservation_app.exception.CinemaRoomNotFoundException;
import com.cinema_reservation_app.exception.MovieAlreadyExistException;
import com.cinema_reservation_app.exception.MovieNotFoundException;
import com.cinema_reservation_app.mapper.MovieMapper;
import com.cinema_reservation_app.repository.CinemaRoomRepo;
import com.cinema_reservation_app.repository.MovieRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {

    private final MovieRepo movieRepo;
    private final CinemaRoomRepo cinemaRoomRepo;
    private final MovieMapper movieMapper;

    public List<MovieResp> getMovieList() {
        List<Movie> movies = movieRepo.findAll();
        return movieMapper.toListMovieResp(movies);
    }

    public MovieResp getMovieById(Long id) {
        Movie movie = movieRepo.findById(id).orElseThrow(() -> new MovieNotFoundException("MOVIE NOT FOUND"));
        return movieMapper.toMovieResp(movie);
    }

    @Transactional
    public MovieResp createMovie(Movie movie) {
        Optional<Movie> existing = movieRepo.findByTitle(movie.getTitle());
        if (existing.isEmpty()) {
            List<Screening> screenings = movie.getScreenings();
            screenings.forEach(screening -> {
                screening.setMovie(movie);
                screening.setCinemaRoom(cinemaRoomRepo.findById(screening.getCinemaRoom().getId()).orElseThrow(() -> new CinemaRoomNotFoundException("CINEMA ROOM NOT FOUND")));
            });
            Movie saved = movieRepo.save(movie);
            log.info("Movie ID: {} added", movie.getId());
            return movieMapper.toMovieResp(saved);
        } else {
            throw new MovieAlreadyExistException("Movie already exist");
        }
    }

}
