package com.baeldung.hexagonal.example.adapter;

import com.baeldung.hexagonal.example.domain.entity.Movie;
import com.baeldung.hexagonal.example.domain.entity.Review;
import com.baeldung.hexagonal.example.domain.service.ReviewMovieService;
import com.baeldung.hexagonal.example.exception.MovieNotFoundException;
import com.baeldung.hexagonal.example.exception.ReviewAlreadyExistsException;
import com.baeldung.hexagonal.example.port.input.ReviewController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ReviewControllerHttpAdapter implements ReviewController {
    private static final String BASE_URI = "/api/v1/movie";

    private ReviewMovieService reviewMovieService;

    public ReviewControllerHttpAdapter(ReviewMovieService reviewMovieService) {
        this.reviewMovieService = reviewMovieService;
    }

    @Override
    @GetMapping(BASE_URI + "/title/{title}")
    public Movie getMovieByTitle(@PathVariable String title) throws MovieNotFoundException {
        return reviewMovieService.viewMoviesByTitle(title);
    }

    @Override
    @GetMapping(BASE_URI + "/director/{director}")
    public Set<Movie> getMoviesByDirector(@PathVariable String director) throws MovieNotFoundException {
        return reviewMovieService.viewMoviesByDirector(director);
    }

    @Override
    @GetMapping(BASE_URI + "/actor/{actor}")
    public Set<Movie> getMovieByActor(@PathVariable String actor) throws MovieNotFoundException {
        return reviewMovieService.viewMoviesByActor(actor);
    }

    @Override
    @PostMapping(BASE_URI + "/review/{title}")
    public void addReviewToMovie(@PathVariable String title, @RequestBody Review review) throws MovieNotFoundException, ReviewAlreadyExistsException {
        reviewMovieService.addReview(review, title);
    }
}
