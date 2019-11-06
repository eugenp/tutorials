package com.baeldung.ports;

import java.util.List;

import com.baeldung.vo.Movie;

/**
 * Primary port
 * @author rchaudhary23
 *
 */
public interface MovieRecommendationService {
	
	public List<Movie> findByYearOfRelease(String year);

	public List<Movie> findByGenre(String genre);

	public List<Movie> findByGenreAndYearOfRelease(String year, String genre);
	
}	
