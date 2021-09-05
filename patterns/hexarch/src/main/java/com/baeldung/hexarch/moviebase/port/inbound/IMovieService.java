package com.baeldung.hexarch.moviebase.port.inbound;

import com.baeldung.hexarch.moviebase.domain.model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * An interface with relevant methods for
 * adding a new movie, deleting an existing
 * one and retrieving the movies in the
 * movie database.
 */
@Service
public interface IMovieService {

    String addMovie(Movie movie);

    String deleteMovie(String movie);

    List<Movie> getMovies();

    Movie getMovie(String id);
}