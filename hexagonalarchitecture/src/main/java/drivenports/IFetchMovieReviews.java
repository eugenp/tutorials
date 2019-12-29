package drivenports;

import model.MovieReview;
import model.MovieSearchRequest;

import java.util.List;

public interface IFetchMovieReviews {
    public List<MovieReview> fetchMovieReviews(MovieSearchRequest movieSearchRequest);
}
