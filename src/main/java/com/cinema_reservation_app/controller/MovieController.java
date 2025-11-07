package com.cinema_reservation_app.controller;

import com.cinema_reservation_app.dto.MovieResp;
import com.cinema_reservation_app.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/all")
    public ResponseEntity<List<MovieResp>> getAllMovies(){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getMovieList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResp> getMovieById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getMovieById(id));
    }


}
