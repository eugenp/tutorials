package com.baeldung.architecture.hexagonal.adapter;

import com.baeldung.architecture.hexagonal.Movie;

import java.util.List;

public class ConsoleMovieDisplayAdapter implements MovieDisplayAdapter {
    @Override
    public void display(List<Movie> matchedMovies) {
        System.out.println(String.format("There are %d movies matched your search", matchedMovies.size()));
        matchedMovies.stream().forEach(m -> System.out.println(m));
    }
}
