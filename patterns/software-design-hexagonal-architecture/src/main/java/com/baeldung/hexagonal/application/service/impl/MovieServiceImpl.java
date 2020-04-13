package com.baeldung.hexagonal.application.service.impl;

import java.util.UUID;

import com.baeldung.hexagonal.domain.Movie;
import com.baeldung.hexagonal.domain.repository.MovieRepository;
import com.baeldung.hexagonal.domain.service.MovieService;

public class MovieServiceImpl implements MovieService {
    private final MovieRepository moviesRepository;

    public MovieServiceImpl(final MovieRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public UUID saveMovie(final String movieName) {
        final Movie movie = new Movie(UUID.randomUUID(), movieName);
        moviesRepository.save(movie);
        return movie.getId();
    }

    @Override
    public UUID addRating(final UUID movieId, final String reviewer, final int noOfStars) {
        final Movie movie = moviesRepository.findById(movieId);
        if (movie == null) {
            throw new IllegalStateException();
        }
        final UUID ratingId = movie.addRating(reviewer, noOfStars);
        moviesRepository.save(movie);
        return ratingId;
    }

    @Override
    public Movie findMovieById(final UUID movieId) {
        return moviesRepository.findById(movieId);
    }

    @Override
    public double getAverageRating(final UUID movieId) {
        final Movie movie = moviesRepository.findById(movieId);
        if (movie == null) {
            throw new IllegalStateException();
        }
        return movie.getAverageRating();
    }
}
