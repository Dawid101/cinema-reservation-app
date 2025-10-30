package com.cinema_reservation_app.service;

import com.cinema_reservation_app.dto.MovieResp;
import com.cinema_reservation_app.dto.ScreeningResp;
import com.cinema_reservation_app.entity.CinemaRoom;
import com.cinema_reservation_app.entity.Movie;
import com.cinema_reservation_app.entity.Screening;
import com.cinema_reservation_app.exception.MovieAlreadyExistException;
import com.cinema_reservation_app.exception.MovieNotFoundException;
import com.cinema_reservation_app.mapper.MovieMapper;
import com.cinema_reservation_app.repository.CinemaRoomRepo;
import com.cinema_reservation_app.repository.MovieRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepo movieRepo;

    @Mock
    private CinemaRoomRepo cinemaRoomRepo;

    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private MovieService movieService;

    private Movie movie;
    private MovieResp movieResp;
    private ScreeningResp screeningResp;

    @BeforeEach
    void setUp(){
        movie = new Movie();
        screeningResp = new ScreeningResp(null, null, null, 0, 0);
        movieResp = new MovieResp(null, null, null, 0, null, List.of(screeningResp));
    }

    @Test
    void getMovieList_shouldReturnListOfMovies(){
        //given
        List<Movie> movies = List.of(movie);
        List<MovieResp> expectedResponse = List.of(movieResp);

        when(movieRepo.findAll()).thenReturn(movies);
        when(movieMapper.toListMovieResp(movies)).thenReturn(expectedResponse);

        //when
        List<MovieResp> result = movieService.getMovieList();

        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(movieRepo).findAll();
        verify(movieMapper).toListMovieResp(movies);
    }

    @Test
    void getMovieById_whenNotExist_shouldThrowException(){
        //given
        when(movieRepo.findById(1L)).thenReturn(Optional.empty());

        //when & then
        assertThrows(MovieNotFoundException.class,
                () -> movieService.getMovieById(1L));
        verify(movieRepo).findById(anyLong());
        verify(movieMapper,never()).toMovieResp(movie);
    }

    @Test
    void getMovieById_whenExist_shouldReturnMovieResp(){
        //given
        when(movieRepo.findById(1L)).thenReturn(Optional.of(movie));
        when(movieMapper.toMovieResp(movie)).thenReturn(movieResp);

        //when
        MovieResp result = movieService.getMovieById(1L);

        //then
        assertNotNull(result);
        verify(movieRepo).findById(1L);
        verify(movieMapper).toMovieResp(movie);
    }

    @Test
    void createMovie_whenNotExist_shouldCreateMovie(){
        //given
        CinemaRoom cinemaRoom = new CinemaRoom();
        cinemaRoom.setId(1L);

        Screening screening = new Screening();
        screening.setCinemaRoom(cinemaRoom);

        movie.setScreenings(List.of(screening));

        when(movieRepo.findByTitle(movie.getTitle())).thenReturn(Optional.empty());
        when(cinemaRoomRepo.findById(screening.getCinemaRoom().getId())).thenReturn(Optional.of(cinemaRoom));
        when(movieRepo.save(movie)).thenReturn(movie);
        when(movieMapper.toMovieResp(movie)).thenReturn(movieResp);

        //when
        MovieResp result = movieService.createMovie(movie);

        //then
        assertNotNull(result);
        verify(movieRepo).findByTitle(movie.getTitle());
        verify(cinemaRoomRepo).findById(1L);
        verify(movieRepo).save(movie);
        verify(movieMapper).toMovieResp(movie);
    }

    @Test
    void createMovie_whenMovieExist_shouldThrowException(){
        //given
        when(movieRepo.findByTitle(movie.getTitle())).thenReturn(Optional.of(movie));

        //when & then
        MovieAlreadyExistException movieAlreadyExistException = assertThrows(MovieAlreadyExistException.class,
                () -> movieService.createMovie(movie));

        assertTrue(movieAlreadyExistException.getMessage().contains("Movie already exist"));
        verify(movieRepo).findByTitle(movie.getTitle());
        verify(movieMapper,never()).toMovieResp(movie);
    }

}