package com.baeldung.hexagonal.domain.repository;

import java.util.UUID;

import com.baeldung.hexagonal.domain.Movie;

public interface MovieRepository {

    void save(Movie movie);

    Movie findById(UUID movieId);

    void delete(UUID movieId);
}
