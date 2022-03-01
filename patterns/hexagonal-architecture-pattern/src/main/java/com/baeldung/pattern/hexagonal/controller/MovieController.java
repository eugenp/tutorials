package com.baeldung.pattern.hexagonal.controller;

import com.baeldung.pattern.hexagonal.domain.model.Movie;
import com.baeldung.pattern.hexagonal.domain.services.MovieService;

public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    public String addMovie(Movie movie) {
        String id = movieService.createMovie(movie);

        return id;
    }

    public Movie findMovie(String id) {
        Movie movie = movieService.getMovie(id);

        return movie;
    }
}
