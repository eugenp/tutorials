package com.baeldung.hexagonal.example.adapter;

import com.baeldung.hexagonal.example.domain.entity.Movie;
import com.baeldung.hexagonal.example.port.output.ReviewStore;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ReviewStoreInMemory implements ReviewStore {

    private Set<Movie> movies;

    public ReviewStoreInMemory() {
        this.movies = new HashSet<>();
    }

    @Override
    public Movie getByTitle(String title) {
        return movies.stream()
          .filter(movie -> movie.getTitle().equalsIgnoreCase(title))
          .findFirst()
          .orElse(null);
    }

    @Override
    public Set<Movie> getByDirector(String director) {
        return movies.stream()
          .filter(movie -> movie.getDirector().equalsIgnoreCase(director))
          .collect(Collectors.toSet());
    }

    @Override
    public Set<Movie> getByActor(String actor) {
        return movies.stream()
          .filter(movie -> movie.getActors().contains(actor))
          .collect(Collectors.toSet());
    }

    @Override
    public void updateMove(Movie movie) {
        movies.add(movie);
    }
}
