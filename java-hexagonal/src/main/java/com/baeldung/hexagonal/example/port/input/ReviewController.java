package com.baeldung.hexagonal.example.port.input;

import com.baeldung.hexagonal.example.domain.entity.Movie;
import com.baeldung.hexagonal.example.domain.entity.Review;
import com.baeldung.hexagonal.example.exception.MovieNotFoundException;
import com.baeldung.hexagonal.example.exception.ReviewAlreadyExistsException;

import java.util.Set;

public interface ReviewController {
    Movie getMovieByTitle(String title) throws MovieNotFoundException;

    Set<Movie> getMoviesByDirector(String director) throws MovieNotFoundException;

    Set<Movie> getMovieByActor(String actor) throws MovieNotFoundException;

    void addReviewToMovie(String movieTitle, Review review) throws MovieNotFoundException, ReviewAlreadyExistsException;
}
