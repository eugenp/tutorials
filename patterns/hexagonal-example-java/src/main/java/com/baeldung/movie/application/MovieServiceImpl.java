package com.baeldung.movie.application;

import java.util.List;

import com.baeldung.movie.application.port.in.MovieServicePort;
import com.baeldung.movie.application.port.out.MoviePersistencePort;
import com.baeldung.movie.domain.MovieDto;

public class MovieServiceImpl implements MovieServicePort{
    
    private MoviePersistencePort moviePersistencePort;
    
    public MovieServiceImpl(MoviePersistencePort moviePersistencePort) {
        this.moviePersistencePort = moviePersistencePort;
    }

    @Override
    public MovieDto addMovie(MovieDto movieDto) {
        return moviePersistencePort.addMovie(movieDto);
    }

    @Override
    public void deleteMovieById(Long id) {
        moviePersistencePort.deleteMovieById(id);    
    }

    @Override
    public MovieDto updateMovie(MovieDto movieDto) {
        return moviePersistencePort.updateMovie(movieDto);
    }

    @Override
    public List<MovieDto> getMovies() {
        return moviePersistencePort.getMovies();
    }

    @Override
    public MovieDto getMovieById(Long movieId) {
        return moviePersistencePort.getMovieById(movieId);
    }

}
