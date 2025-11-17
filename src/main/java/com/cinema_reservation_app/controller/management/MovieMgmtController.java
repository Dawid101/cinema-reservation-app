package com.cinema_reservation_app.controller.management;

import com.cinema_reservation_app.dto.MovieResp;
import com.cinema_reservation_app.entity.Movie;
import com.cinema_reservation_app.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/movie")
@RequiredArgsConstructor
public class MovieMgmtController {
    private final MovieService movieService;

    @PostMapping("/add")
    public ResponseEntity<MovieResp> addMovie(@RequestBody Movie movie){
        MovieResp saved = movieService.createMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
