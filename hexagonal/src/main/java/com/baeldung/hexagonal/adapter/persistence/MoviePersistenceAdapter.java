package com.baeldung.hexagonal.adapter.persistence;

import com.baeldung.hexagonal.application.port.output.FindMovies;
import com.baeldung.hexagonal.domain.Movie;

import java.util.Set;

public class MoviePersistenceAdapter implements FindMovies {

    private MovieRepository movieRepository;

    public MoviePersistenceAdapter(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Set<Movie> byGenre(Movie.Genre genre) {
        return movieRepository.findMoviesByGenre(genre);
    }
}
