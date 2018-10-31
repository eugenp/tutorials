package com.baeldung.hexa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.baeldung.hexa.movie.domain.Movie;
import com.baeldung.hexa.movie.domain.MovieRepository;
import com.baeldung.hexa.movie.domain.exception.MovieNotFoundException;

public class InMemoryMovieRepository implements MovieRepository {
	
	private ConcurrentHashMap<String, Movie> map = new ConcurrentHashMap<String, Movie>(); 

	public Movie save(Movie movie) {
		map.put(movie.getTitle(), movie);
		return movie;
	}

	public Movie findOne(String title) {
		
		Movie movie = map.get(title);
		if (movie == null) {
			throw new MovieNotFoundException(title);
		}
		return movie;
	}

	public List<Movie> findAll() {
		return new ArrayList<Movie>(map.values());
	}
}
