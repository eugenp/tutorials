package com.baeldung.hexagonal.example.test.helper;

import com.baeldung.hexagonal.example.domain.entity.Movie;
import com.baeldung.hexagonal.example.port.output.ReviewStore;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;

import java.util.Set;

public class MockDatabaseAdapter implements ReviewStore {

    private Movie mockEntry;

    public void setMockEntry(Movie mockEntry) {
        this.mockEntry = mockEntry;
    }

    @Override
    public Movie getByTitle(String title) {
        return mockEntry;
    }

    @Override
    public Set<Movie> getByDirector(String director) {
        return mockEntry == null ? emptySet() : singleton(mockEntry);
    }

    @Override
    public Set<Movie> getByActor(String actor) {
        return mockEntry == null ? emptySet() : singleton(mockEntry);
    }

    @Override
    public void updateMove(Movie movie) {
        mockEntry.setTitle(movie.getTitle());
        mockEntry.setDirector(movie.getDirector());
        mockEntry.setActors(movie.getActors());
        mockEntry.setReviews(movie.getReviews());
    }
}
