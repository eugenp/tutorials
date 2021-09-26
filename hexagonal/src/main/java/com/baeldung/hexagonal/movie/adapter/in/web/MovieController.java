package com.baeldung.hexagonal.movie.adapter.in.web;

import java.util.List;

import com.baeldung.hexagonal.movie.application.port.in.MovieQuery;
import com.baeldung.hexagonal.movie.domain.Genre;
import com.baeldung.hexagonal.movie.domain.Movie;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

	private final MovieQuery movieQuery;

	@GetMapping(path = "/bygenre/{genre}")
	List<Movie> getMovie(@PathVariable("genre") String genre) {

        return movieQuery.getMovieByGenre(Genre.valueOf(genre.toUpperCase()));
	}
}
