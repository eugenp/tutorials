package com.baeldung.architecture.hexagonal.service;

import java.util.List;
import java.util.Optional;

import com.baeldung.architecture.hexagonal.domain.Movie;

public interface MovieService {

    public Movie createMovie(Movie movie);

    public void deleteMovie(Movie movie);

    public List<Movie> getAllMovies();

    public Optional<Movie> findMovieById(int id);

}