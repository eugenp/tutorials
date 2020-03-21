package com.baeldung.architecture.hexagonal.adapter;

import com.baeldung.architecture.hexagonal.Movie;

import java.util.List;

public interface MovieDisplayAdapter {
    void display(List<Movie> matchedMovies);
}
