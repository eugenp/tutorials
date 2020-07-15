package com.baeldung.hexagonalexample.ports.inbound;

import com.baeldung.hexagonalexample.entity.MovieEntity;
import com.baeldung.hexagonalexample.model.Movie;

import java.util.List;

public interface TopRatedMovieServicePort {
    List<MovieEntity> topRateMovies(String genre);

    void addMovie(MovieEntity movieEntity);
}
