package com.baeldung.hexagonal.architecture.port;

import java.util.List;

import com.baeldung.hexagonal.architecture.model.Movie;

/**
 * The repository interface is an outbound port that enables communication from the core application to a database
 */
public interface MovieRepository  {

    List<Movie> getMovies();

    Movie getMovieById(Integer movieId);

    Movie addMovie(Movie movie);

    Movie removeMovie(Integer movieId);
}
