package com.baeldung.hexagonal.architecture.port;

import java.util.List;

import com.baeldung.hexagonal.architecture.model.Movie;

/**
 * The interface is an inbound port provides the flow and the application functionality to the outside
 */
public interface MovieService {

    List<Movie> getMovies();

    Movie getMovieById(Integer movieId);

    Movie addMovie(Movie movie);

    Movie removeMovie(Integer movieId);
}
