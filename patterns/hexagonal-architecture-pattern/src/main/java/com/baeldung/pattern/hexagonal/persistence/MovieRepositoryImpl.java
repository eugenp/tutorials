package com.baeldung.pattern.hexagonal.persistence;

import java.util.HashMap;
import java.util.Map;

import com.baeldung.pattern.hexagonal.domain.model.Movie;
import com.baeldung.pattern.hexagonal.domain.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {

    private static final Map<String, Movie> moviesMap = new HashMap<>();

    @Override
    public Movie save(Movie movie) {
        moviesMap.put(movie.getId(), movie);
        return movie;
    }

    @Override
    public Movie findById(String id) {
        return moviesMap.get(id);
    }

}
