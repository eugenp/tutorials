package com.baeldung.hexagonalarchitecture.drivenports;

import com.baeldung.hexagonalarchitecture.model.MovieReview;
import com.baeldung.hexagonalarchitecture.model.MovieSearchRequest;

import java.util.List;

public interface IFetchMovieReviews {
    public List<MovieReview> fetchMovieReviews(MovieSearchRequest movieSearchRequest);
}
