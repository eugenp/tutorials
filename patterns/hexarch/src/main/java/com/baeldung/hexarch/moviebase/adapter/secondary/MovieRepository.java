package com.baeldung.hexarch.moviebase.adapter.secondary;

import com.baeldung.hexarch.moviebase.domain.model.Movie;
import com.baeldung.hexarch.moviebase.port.outbound.IMovieRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MovieRepository implements IMovieRepository {

    private static final List<Movie> movies = new ArrayList<>();

    @Override
    public List<Movie> getMovies() {
        return movies;
    }

    @Override
    public Movie getMovie(String id) {

        for (Movie movie : movies)
            if (movie.getId().equals(id)) {
                return movie;
            }
        return null;
    }

    @Override
    public void addMovie(Movie movie) {
        if (!movies.contains(movie)) {
            movies.add(movie);
        }
    }

    @Override
    public void deleteMovie(String id) {
        movies.removeIf(movie -> movie.getId().equals(id));
    }
}
