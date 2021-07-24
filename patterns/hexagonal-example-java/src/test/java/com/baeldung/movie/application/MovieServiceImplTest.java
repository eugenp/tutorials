package com.baeldung.movie.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.movie.application.port.in.MovieServicePort;
import com.baeldung.movie.application.port.out.MoviePersistencePort;
import com.baeldung.movie.domain.MovieDto;


public class MovieServiceImplTest {
    
    private MoviePersistencePort moviePersistencePort;
    private MovieServicePort movieServicePort;
    private MovieDto movieDto;
    
    @BeforeEach
    void setUp() {
        moviePersistencePort = mock(MoviePersistencePort.class);
        movieServicePort = new MovieServiceImpl(moviePersistencePort);
        movieDto = new MovieDto();
        movieDto.setId(Long.valueOf(2));
        movieDto.setDescription("Action");
        movieDto.setTitle("Terminator");
        movieDto.setPrice(Double.valueOf(40));
    }
    
    @Test
    void addMovie() {
        when(moviePersistencePort.addMovie(any(MovieDto.class))).thenReturn(movieDto);
        MovieDto testResponse = movieServicePort.addMovie(movieDto);
        assertEquals(movieDto, testResponse);
    }

    @Test
    void getMovie() {
        when(moviePersistencePort.getMovieById(Long.valueOf(2))).thenReturn(movieDto);
        MovieDto testResponse = movieServicePort.getMovieById(Long.valueOf(2));
        assertEquals(movieDto, testResponse);
    }

    @Test
    void deleteMovieById() {
        doNothing().when(moviePersistencePort.getMovieById(Long.valueOf(2)));
        MovieDto testResponse = movieServicePort.getMovieById(Long.valueOf(2));
        assertEquals(null, testResponse);
    }
    
    @Test
    void getMovies() {
        doNothing().when(moviePersistencePort.getMovies());
        assertEquals(1, moviePersistencePort.getMovies().size());
    }
    
}
