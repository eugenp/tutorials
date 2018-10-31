package com.baeldung.hexa.movie.domain;

import java.util.List;

public interface MovieService {

	void addMovie(MovieDTO movie);
	
	MovieDTO findMovie(String title);
	
	List<MovieDTO> listMovies();
}
