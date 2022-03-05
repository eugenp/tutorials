package com.baeldung.pattern.hexagonal.domain.services;

import java.util.UUID;

import com.baeldung.pattern.hexagonal.domain.model.Movie;
import com.baeldung.pattern.hexagonal.domain.repository.MovieRepository;

public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie getMovie(String id) {
        return movieRepository.findById(id);
    }
}
