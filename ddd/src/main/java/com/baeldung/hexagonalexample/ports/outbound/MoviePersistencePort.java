package com.baeldung.hexagonalexample.ports.outbound;

import com.baeldung.hexagonalexample.entity.MovieEntity;

import java.util.List;

public interface MoviePersistencePort {
    List<MovieEntity> findByGenre(String genre);
    void save(MovieEntity movie);
}
