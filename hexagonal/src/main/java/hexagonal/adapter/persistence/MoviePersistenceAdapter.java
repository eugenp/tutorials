package hexagonal.adapter.persistence;

import hexagonal.application.port.output.FindMovies;
import hexagonal.domain.Movie;

import java.util.Set;

public class MoviePersistenceAdapter implements FindMovies {

    private MovieRepository movieRepository;

    public MoviePersistenceAdapter(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Set<Movie> byGenre(String genre) {
        return movieRepository.findMoviesByGenre(genre);
    }
}
