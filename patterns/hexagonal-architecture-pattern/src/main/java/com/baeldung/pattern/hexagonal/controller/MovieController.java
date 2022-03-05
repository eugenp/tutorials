package com.baeldung.pattern.hexagonal.controller;

import com.baeldung.pattern.hexagonal.domain.model.Movie;
import com.baeldung.pattern.hexagonal.domain.services.MovieService;

public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    public Movie addMovie(Movie movie) {
        Movie createdMovie = movieService.createMovie(movie);

        return createdMovie;
    }

    public Movie findMovie(String id) {
        Movie movie = movieService.getMovie(id);

        return movie;
    }
}
