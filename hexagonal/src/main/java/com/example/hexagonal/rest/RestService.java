package com.example.hexagonal.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hexagonal.adapter.MovieHubAdapter;
import com.example.hexagonal.domain.Movie;

@RestController
public class RestService {
	
	@Autowired
	MovieHubAdapter rental;			

	@GetMapping("/getmovie")
	public Movie getMovieTitleNumber(@RequestParam(value = "title") String name) {
		return rental.getMovieDetails(name);
	}

	@PostMapping("/adduser")
	public String registerNewUser(@RequestParam(value = "name") String userName,
			@RequestParam(value = "location") String userLocation) {
		return rental.registerNewUser(userName, userLocation);
	}
		
	@PostMapping("/addmovie")
	public String addMovie(
			@RequestParam(value = "id") int movieId,
			@RequestParam(value = "name") String movieName,
			@RequestParam(value = "year") int releaseYear, 
			@RequestParam(value = "imdb") double imdbScore,
			@RequestParam(value = "rt") int rtScore) {		
		return rental.addMovie(movieId, movieName, releaseYear, imdbScore, rtScore);
	}
}
