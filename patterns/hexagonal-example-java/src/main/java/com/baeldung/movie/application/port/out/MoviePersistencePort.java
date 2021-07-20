package com.baeldung.movie.application.port.out;

import java.util.List;

import com.baeldung.movie.domain.MovieDto;

public interface MoviePersistencePort {
    
    MovieDto addMovie(MovieDto movieDto);

    void deleteMovieById(Long id);

    MovieDto updateMovie(MovieDto movieDto);

    List<MovieDto> getMovies();

    MovieDto getMovieById(Long movieId);
    
}
