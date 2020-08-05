package com.baeldung.hexagonal.outbound.repo;

import com.baeldung.hexagonal.model.Movie;

import java.util.List;

public interface MovieDao {

    List<Movie> getAllMovies();

    void addNewMovie(Movie movie);
}
