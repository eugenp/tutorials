package com.baeldung.architecture.hexagonal.adapter;

import com.baeldung.architecture.hexagonal.Movie;

import java.io.IOException;
import java.util.List;

public interface MovieDataSourceAdapter {
    List<Movie> allMovies() throws IOException;
}
