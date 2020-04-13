package com.baeldung.hexagonal.application.service.impl;

import java.util.Set;

import com.baeldung.hexagonal.domain.Movie;
import com.baeldung.hexagonal.domain.repository.MovieSearchRepository;
import com.baeldung.hexagonal.domain.service.MovieSearchService;

public class MovieSearchServiceImpl implements MovieSearchService {
    private MovieSearchRepository movieFinderRepository;

    public MovieSearchServiceImpl(MovieSearchRepository movieFinderRepository) {
        this.movieFinderRepository = movieFinderRepository;
    }

    @Override
    public Set<Movie> searchMovieByName(String movieName) {
        return movieFinderRepository.searchMoviesByName(movieName);
    }

    @Override
    public Movie findByName(String movieName) {
        return movieFinderRepository.findMovieByName(movieName);
    }
}
