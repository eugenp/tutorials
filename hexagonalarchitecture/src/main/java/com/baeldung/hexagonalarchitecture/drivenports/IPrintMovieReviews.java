package com.baeldung.hexagonalarchitecture.drivenports;

import com.baeldung.hexagonalarchitecture.model.MovieReview;

import java.util.List;

public interface IPrintMovieReviews {
    public void writeMovieReviews(List<MovieReview> movieReviewList);
}
