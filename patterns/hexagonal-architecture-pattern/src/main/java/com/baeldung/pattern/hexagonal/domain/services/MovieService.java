package com.baeldung.pattern.hexagonal.domain.services;

import com.baeldung.pattern.hexagonal.domain.model.Movie;

public interface MovieService {

    String createMovie(Movie movie);

    Movie getMovie(String id);
}
