package com.baeldung.hexagonal.example.domain.service;

import com.baeldung.hexagonal.example.domain.entity.Movie;
import com.baeldung.hexagonal.example.domain.entity.Review;
import com.baeldung.hexagonal.example.exception.MovieNotFoundException;
import com.baeldung.hexagonal.example.exception.ReviewAlreadyExistsException;
import com.baeldung.hexagonal.example.port.output.ReviewStore;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ReviewMovieService {

    private ReviewStore reviewStore;

    public ReviewMovieService(ReviewStore reviewStore) {
        this.reviewStore = reviewStore;
    }

    public Movie viewMoviesByTitle(String title) throws MovieNotFoundException {
        Movie movie = reviewStore.getByTitle(title);

        if (movie == null) {
            throw new MovieNotFoundException();
        }

        return movie;
    }

    public Set<Movie> viewMoviesByDirector(String director) throws MovieNotFoundException {
        Set<Movie> movies = reviewStore.getByDirector(director);

        if (movies == null || movies.isEmpty()) {
            throw new MovieNotFoundException();
        }

        return movies;
    }

    public Set<Movie> viewMoviesByActor(String actor) throws MovieNotFoundException {
        Set<Movie> movies = reviewStore.getByActor(actor);

        if (movies == null || movies.isEmpty()) {
            throw new MovieNotFoundException();
        }

        return movies;
    }

    public void addReview(
            Review review, String movieTitle) throws MovieNotFoundException, ReviewAlreadyExistsException {
        Movie movie = viewMoviesByTitle(movieTitle);

        boolean alreadyReviewed = movie.getReviews()
          .stream()
          .anyMatch(rev -> rev.getAuthor().equalsIgnoreCase(review.getAuthor()));

        if (alreadyReviewed) {
            throw new ReviewAlreadyExistsException();
        }

        movie.getReviews().add(review);
        reviewStore.updateMove(movie);
    }
}
