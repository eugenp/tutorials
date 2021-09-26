package com.baeldung.hexagonal.movie.application.service;

import java.util.List;
import java.util.stream.Collectors;

import com.baeldung.hexagonal.movie.application.port.in.MovieQuery;
import com.baeldung.hexagonal.movie.application.port.out.MovieRepository;
import com.baeldung.hexagonal.movie.domain.Genre;
import com.baeldung.hexagonal.movie.domain.Movie;
import com.baeldung.hexagonal.movie.domain.Price;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieService implements MovieQuery {

    private final MovieRepository movieRepository;

    @Override
    public List<Movie> getMovieByGenre(Genre genre){
        return movieRepository.getAllMovies()
                              .stream()
                              .filter(movie -> movie.getGenre().equals(genre))
                              .collect(Collectors.toList());
    }

    @Override
    public List<Movie> getMovieBetweenRentPrice(Price low, Price high){
        return movieRepository.getAllMovies()
                              .stream()
                              .filter(movie -> movie.getRentPrice().between(low, high))
                              .collect(Collectors.toList());
    }
}
