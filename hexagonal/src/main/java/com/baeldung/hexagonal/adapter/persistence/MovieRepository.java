package com.baeldung.hexagonal.adapter.persistence;

import com.baeldung.hexagonal.domain.Movie;

import java.util.Set;

public interface MovieRepository {
    Set<Movie> findMoviesByGenre(Movie.Genre genre);
}
