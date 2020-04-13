package com.baeldung.hexagonal.domain.service;

import java.util.Set;

import com.baeldung.hexagonal.domain.Movie;

public interface MovieSearchService {

    public Set<Movie> searchMovieByName(String movieName);

    public Movie findByName(String movieName);
}
