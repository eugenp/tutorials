package com.baeldung.hexagonal.application.port.output;

import com.baeldung.hexagonal.domain.Movie;

import java.util.Set;

public interface FindMovies {
    Set<Movie> byGenre(Movie.Genre genre);
}
