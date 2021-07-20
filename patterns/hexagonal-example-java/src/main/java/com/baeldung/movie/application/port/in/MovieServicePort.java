package com.baeldung.movie.application.port.in;

import java.util.List;

import com.baeldung.movie.domain.MovieDto;

public interface MovieServicePort {
    
    MovieDto addMovie(MovieDto movieDto);

    void deleteMovieById(Long id);

    MovieDto updateMovie(MovieDto movieDto);

    List<MovieDto> getMovies();

    MovieDto getMovieById(Long movieId);

}
