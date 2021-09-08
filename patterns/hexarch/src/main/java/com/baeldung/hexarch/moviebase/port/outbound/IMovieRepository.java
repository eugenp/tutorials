package com.baeldung.hexarch.moviebase.port.outbound;

import com.baeldung.hexarch.moviebase.domain.model.Movie;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * An interface with relevant methods for
 * adding a new movie, deleting and retrieving
 * the movies in the movie database.
 */
@Repository
public interface IMovieRepository {

    void addMovie(Movie movie);

    void deleteMovie(String id);

    List<Movie> getMovies();

    Movie getMovie(String id);
}
