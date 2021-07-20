package com.baeldung.movie.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.movie.application.port.out.MoviePersistencePort;
import com.baeldung.movie.domain.MovieDto;

public class MovieJpaAdapter implements MoviePersistencePort{
    
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public MovieDto addMovie(MovieDto movieDto) {
        Movie movie = MovieMapper.INSTANCE.movieDtoToMovie(movieDto);

        Movie movieSaved = movieRepository.save(movie);

        return MovieMapper.INSTANCE.movieToMovieDto(movieSaved);
    }

    @Override
    public void deleteMovieById(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public MovieDto updateMovie(MovieDto movieDto) {
        return addMovie(movieDto);
    }

    @Override
    public List<MovieDto> getMovies() {
        List<Movie> movieList = movieRepository.findAll();
        return MovieMapper.INSTANCE.movieListToMovieDtoList(movieList);
    }

    @Override
    public MovieDto getMovieById(Long movieId) {
        Optional<Movie> movie = movieRepository.findById(movieId);

        if (movie.isPresent()) {
            return MovieMapper.INSTANCE.movieToMovieDto(movie.get());
        }
        
        return null;
    }

}
