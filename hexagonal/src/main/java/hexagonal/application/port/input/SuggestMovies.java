package hexagonal.application.port.input;

import hexagonal.domain.Movie;

import java.util.Set;

public interface SuggestMovies {
    Set<Movie> toUserByName(String name);
}
