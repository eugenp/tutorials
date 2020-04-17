package com.baeldung.hexagonal.domain;

import java.util.List;
import java.util.stream.Collectors;

import com.baeldung.hexagonal.domain.inputPorts.RetrieveByRatingPort;
import com.baeldung.hexagonal.domain.outputPorts.Persistence;

public class RetrieveByRatingUseCase implements RetrieveByRatingPort {

    private Persistence movieRetriever;

    public RetrieveByRatingUseCase(Persistence movieRetriever) {
        this.movieRetriever = movieRetriever;
    }

    public List<MovieData> retrieveMoviesAbove(float rating) {
        List<MovieData> movies = movieRetriever.retrieveMovies();

        List<MovieData> goodMovies = movies.stream()
            .filter(movie -> movie.getRating() > rating)
            .collect(Collectors.toList());

        return goodMovies;
    }

}
