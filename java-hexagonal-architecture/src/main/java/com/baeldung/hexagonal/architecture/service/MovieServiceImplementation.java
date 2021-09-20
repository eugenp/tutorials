package com.baeldung.hexagonal.architecture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.architecture.model.Movie;
import com.baeldung.hexagonal.architecture.port.MovieRepository;
import com.baeldung.hexagonal.architecture.port.MovieService;

/**
 * The class is an use case implementation of the inbound port.
 */
@Service
public class MovieServiceImplementation implements MovieService {
    
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> getMovies() {
        return movieRepository.getMovies();
    }

    @Override
    public Movie getMovieById(Integer movieId) {
        return movieRepository.getMovieById(movieId);
    }

    @Override
    public Movie addMovie(Movie movie) {
        return movieRepository.addMovie(movie);
    }

    @Override
    public Movie removeMovie(Integer movieId) {
        return movieRepository.removeMovie(movieId);
    }

}
