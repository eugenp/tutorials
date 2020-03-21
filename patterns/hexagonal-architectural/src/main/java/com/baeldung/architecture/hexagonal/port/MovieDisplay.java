package com.baeldung.architecture.hexagonal.port;

import com.baeldung.architecture.hexagonal.Movie;

import java.util.List;

public interface MovieDisplay {
    void display(List<Movie> matchedMovies);
}
