package com.baeldung.hexagonal.architecture.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.architecture.model.Movie;
import com.baeldung.hexagonal.architecture.port.MovieRepository;

/**
 * The class is an use case implementation of outbound port.
 */
@Repository
public class MovieRepositoryImplementation implements MovieRepository {

    private static final Map<Integer, Movie> movieMap = new HashMap<Integer, Movie>(0);

    @Override
    public List<Movie> getMovies() {
        return new ArrayList<Movie>(movieMap.values());
    }

    @Override
    public Movie getMovieById(Integer movieId) {
        return movieMap.get(movieId);
    }

    @Override
    public Movie addMovie(Movie movie) {
        movieMap.put(movie.getMovieId(), movie);
        return movie;
    }

    @Override
    public Movie removeMovie(Integer movieId) {
        if (movieMap.get(movieId) != null) {
            Movie movie = movieMap.get(movieId);
            movieMap.remove(movieId);
            return movie;
        } else {
            return null;
        }
    }

}
