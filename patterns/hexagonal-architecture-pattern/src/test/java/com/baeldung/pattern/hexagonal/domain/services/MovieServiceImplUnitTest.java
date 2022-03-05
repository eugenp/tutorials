package com.baeldung.pattern.hexagonal.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.pattern.hexagonal.domain.model.Movie;
import com.baeldung.pattern.hexagonal.domain.repository.MovieRepository;

public class MovieServiceImplUnitTest {

    private MovieRepository movieRepository;
    private MovieService movieService;
    private Movie testModel;

    @BeforeEach
    void setUp() {
        movieRepository = mock(MovieRepository.class);

        movieService = new MovieServiceImpl(movieRepository);
        testModel = new Movie("M1", "Baeldung");
    }

    @Test
    void addMovie() {
        when(movieRepository.save(any(Movie.class))).thenReturn(testModel);

        Movie testResponse = movieService.createMovie(testModel);
        assertEquals(testModel, testResponse);
    }

    @Test
    void getMovie() {
        when(movieRepository.findById("2000")).thenReturn(testModel);

        Movie testResponse = movieService.getMovie("2000");
        assertEquals(testModel, testResponse);
    }
}
