package com.example.hexagonal.port;

import com.example.hexagonal.domain.Movie;
/**
 * exposes a point for interaction with the outside world
 * and sets the protocol for adapter
 *
 */
public interface MovieHubPort {	
	Movie getMovieDetails(String movieTitle);
	String registerNewUser(String userName, String userLocation);
	String addMovie(int id, String title, int releaseYear, double imdbRating, int rottenTomatoesScore);
}
