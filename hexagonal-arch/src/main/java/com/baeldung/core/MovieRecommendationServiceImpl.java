package com.baeldung.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.ports.MovieRecommendationService;
import com.baeldung.ports.MovieRepository;
import com.baeldung.vo.Movie;

/**
 * Core business logic
 * @author rchaudhary23
 *
 */
@Service
public class MovieRecommendationServiceImpl implements MovieRecommendationService {

	@Autowired
	private MovieRepository repo;

	@Override
	public List<Movie> findByYearOfRelease(String year) {
		return repo.findByYearOfRelease(year);
	}

	@Override
	public List<Movie> findByGenre(String genre) {
		return repo.findByMovieGenre(genre);
	}

	@Override
	public List<Movie> findByGenreAndYearOfRelease(String year, String genre) {
		return repo.findByMovieGenreAndYearOfRelease(genre, year);
	}
}
