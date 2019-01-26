package com.baeldung.architecture.hexagonal.repository;

import java.util.List;
import java.util.Optional;

import com.baeldung.architecture.hexagonal.domain.Movie;

public interface MovieRepository {

    public Movie create(Movie movie);

    public Movie delete(Movie movie);

    public List<Movie> findAll();

    public Optional<Movie> findById(int movieId);

}