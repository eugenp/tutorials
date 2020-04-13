package com.baeldung.hexagonal.domain.repository;

import java.util.Set;

import com.baeldung.hexagonal.domain.Movie;

public interface MovieSearchRepository {

    Movie findMovieByName(String movieName);

    Set<Movie> searchMoviesByName(String movieName);
}
