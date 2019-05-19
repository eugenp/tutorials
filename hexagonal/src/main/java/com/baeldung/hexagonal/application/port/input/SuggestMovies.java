package com.baeldung.hexagonal.application.port.input;

import com.baeldung.hexagonal.domain.Movie;

import java.util.Set;

public interface SuggestMovies {
    Set<Movie> toUserByName(String name);
}
