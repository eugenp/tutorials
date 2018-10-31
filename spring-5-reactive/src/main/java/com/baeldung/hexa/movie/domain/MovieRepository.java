package com.baeldung.hexa.movie.domain;

import java.util.List;

public interface MovieRepository {
	
	Movie save(Movie movie);
	
	Movie findOne(String title);
	
	List<Movie> findAll();
}
