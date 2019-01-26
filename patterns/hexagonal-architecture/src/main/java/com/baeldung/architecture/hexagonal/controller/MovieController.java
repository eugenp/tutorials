package com.baeldung.architecture.hexagonal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baeldung.architecture.hexagonal.domain.Movie;
import com.baeldung.architecture.hexagonal.service.MovieService;

@RequestMapping(value = "/api/v1/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Movie> create(@RequestBody @Validated Movie movie) {

        Movie movieCreated = movieService.createMovie(movie);

        Movie output = Movie.fromMovieDetails(movieCreated);
        return new ResponseEntity<Movie>(output, HttpStatus.CREATED);
    }

}