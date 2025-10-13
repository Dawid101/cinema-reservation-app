package com.cinema_reservation_app.controller;

import com.cinema_reservation_app.dto.MovieResp;
import com.cinema_reservation_app.entity.Movie;
import com.cinema_reservation_app.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cinema-movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping("/add")
    public ResponseEntity<MovieResp> addMovie(@RequestBody Movie movie){
        MovieResp saved = movieService.createMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/all-movies")
    public ResponseEntity<List<MovieResp>> getAllMovies(){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getMovieList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResp> getMovieById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getMovieById(id));
    }
}
