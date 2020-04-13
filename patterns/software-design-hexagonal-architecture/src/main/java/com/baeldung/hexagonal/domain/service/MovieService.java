package com.baeldung.hexagonal.domain.service;

import java.util.UUID;

import com.baeldung.hexagonal.domain.Movie;

public interface MovieService {

    public UUID saveMovie(String movieName);

    public UUID addRating(UUID movieId, String reviewer, int noOfStars);

    public Movie findMovieById(UUID movieId);

    public double getAverageRating(UUID movieId);
}
