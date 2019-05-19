package hexagonal.adapter.persistence;

import hexagonal.domain.Movie;

import java.util.Set;

public interface MovieRepository {
    Set<Movie> findMoviesByGenre(String genre);
}
