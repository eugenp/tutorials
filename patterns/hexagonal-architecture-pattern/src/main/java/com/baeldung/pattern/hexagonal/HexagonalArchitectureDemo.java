package com.baeldung.pattern.hexagonal;

import com.baeldung.pattern.hexagonal.controller.MovieController;
import com.baeldung.pattern.hexagonal.domain.model.Movie;
import com.baeldung.pattern.hexagonal.domain.repository.MovieRepository;
import com.baeldung.pattern.hexagonal.domain.services.MovieService;
import com.baeldung.pattern.hexagonal.domain.services.MovieServiceImpl;
import com.baeldung.pattern.hexagonal.persistence.MovieRepositoryImpl;

public class HexagonalArchitectureDemo {

    public static void main(String[] args) {

        MovieRepository movieRepository = new MovieRepositoryImpl();
        MovieService movieService = new MovieServiceImpl(movieRepository);
        MovieController movieController = new MovieController(movieService);

        Movie movie = new Movie("Baeldung");

        String movieId = movieController.addMovie(movie);
        System.out.println("Movie added with id : " + movieId);

        Movie returnedMovie = movieController.findMovie(movieId);
        System.out.println("Searched movie id : " + returnedMovie.getId() + " name : " + returnedMovie.getName());
    }

}
