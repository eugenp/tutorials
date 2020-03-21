package com.baeldung.architecture.hexagonal.port;

import com.baeldung.architecture.hexagonal.Movie;

import java.io.IOException;
import java.util.List;

public interface MovieDataSource {
    List<Movie> allMovies() throws IOException;
}
