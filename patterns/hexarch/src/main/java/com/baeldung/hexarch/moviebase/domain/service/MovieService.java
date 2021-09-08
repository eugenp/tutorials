package com.baeldung.hexarch.moviebase.domain.service;

import com.baeldung.hexarch.moviebase.adapter.secondary.MovieRepository;
import com.baeldung.hexarch.moviebase.domain.model.Movie;
import com.baeldung.hexarch.moviebase.domain.objects.MethodResult;
import com.baeldung.hexarch.moviebase.port.inbound.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * An implementation class for a {@link IMovieService} interface.
 */
public class MovieService implements IMovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public String addMovie(Movie movie) {

        UUID uuid = UUID.randomUUID();
        movie.setId(uuid.toString());

        movieRepository.addMovie(movie);

        return uuid.toString();
    }

    @Override
    public String deleteMovie(String movieId) {

        movieRepository.deleteMovie(movieId);
        return MethodResult.SUCCESS.name();

    }

    @Override
    public List<Movie> getMovies() {
        return movieRepository.getMovies();
    }

    @Override
    public Movie getMovie(String id) {
        return movieRepository.getMovie(id);
    }
}
