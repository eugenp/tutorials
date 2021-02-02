package com.baeldung.hexagonal.example.port.output;

import com.baeldung.hexagonal.example.domain.entity.Movie;

import java.util.Set;

public interface ReviewStore {
    Movie getByTitle(String title);

    Set<Movie> getByDirector(String director);

    Set<Movie> getByActor(String actor);

    void updateMove(Movie movie);
}
