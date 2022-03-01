package com.baeldung.pattern.hexagonal.domain.repository;

import com.baeldung.pattern.hexagonal.domain.model.Movie;

public interface MovieRepository {
    Movie findById(String id);

    void save(Movie movie);
}
