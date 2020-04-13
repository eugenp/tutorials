package com.baeldung.hexagonal.repository.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.baeldung.hexagonal.domain.Movie;
import com.baeldung.hexagonal.domain.repository.MovieRepository;
import com.baeldung.hexagonal.domain.repository.MovieSearchRepository;

import java.util.Set;
import java.util.UUID;

/**
 * {@link MovieRepository} implementation that stores movies in-memory.
 */
public class InMemoryMovieRepository implements MovieRepository, MovieSearchRepository {
    private final Map<UUID, Movie> moviesDB = new HashMap<>();

    @Override
    public Set<Movie> searchMoviesByName(final String movieName) {
        return moviesDB.entrySet()
            .stream()
            .filter(entry -> entry.getValue()
                .getName()
                .contains(movieName))
            .map(Entry::getValue)
            .collect(Collectors.toSet());
    }

    @Override
    public void save(final Movie movie) {
        moviesDB.put(movie.getId(), movie);
    }

    @Override
    public Movie findById(final UUID movieId) {
        return moviesDB.get(movieId);
    }

    @Override
    public Movie findMovieByName(final String movieName) {
        return moviesDB.entrySet()
            .stream()
            .filter(entry -> entry.getValue()
                .getName()
                .equals(movieName))
            .map(Entry::getValue)
            .findFirst()
            .orElse(null);
    }

    @Override
    public void delete(final UUID movieId) {
        moviesDB.remove(movieId);
    }

}
