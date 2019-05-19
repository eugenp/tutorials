package hexagonal.application.port.output;

import hexagonal.domain.Movie;

import java.util.Set;

public interface FindMovies {
    Set<Movie> byGenre(String genre);
}
