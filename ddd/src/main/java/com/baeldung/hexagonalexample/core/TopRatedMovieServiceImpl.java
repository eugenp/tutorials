package com.baeldung.hexagonalexample.core;

import com.baeldung.hexagonalexample.entity.MovieEntity;
import com.baeldung.hexagonalexample.ports.inbound.TopRatedMovieServicePort;
import com.baeldung.hexagonalexample.ports.outbound.MoviePersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopRatedMovieServiceImpl implements TopRatedMovieServicePort {

    @Autowired
    private MoviePersistencePort moviePersistencePort;

    @Override
    public List<MovieEntity> topRateMovies(String genre) {
        List<MovieEntity> movies = moviePersistencePort.findByGenre(genre.toLowerCase());
        // Some business logic of filtering out top rated movies
        // For now, we are returning the list as is
        return movies;
    }

    @Override
    public void addMovie(MovieEntity movieEntity) {
        moviePersistencePort.save(movieEntity);
    }
}
