package com.baeldung.hexa.movie.domain;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.hexa.movie.domain.exception.MovieNotFoundException;

public class MovieServiceImpl implements MovieService {

	private MovieRepository movieRepository;
	
	public MovieServiceImpl(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	public void addMovie(MovieDTO movie) {		
		Movie entity = new Movie();
		entity.setTitle(movie.getTitle());
		entity.setProductionYear(movie.getProductionYear());
		movieRepository.save(entity);		
	}

	public MovieDTO findMovie(String title) {
		try {
			Movie result = movieRepository.findOne(title);
			return new MovieDTO(result.getTitle(), result.getProductionYear());
		} catch(MovieNotFoundException e) {
			return null;
		}		
	}

	public List<MovieDTO> listMovies() {

		List<MovieDTO> result = new ArrayList<MovieDTO>();
		
		movieRepository.findAll().forEach(item -> {
			result.add(new MovieDTO(item.getTitle(), item.getProductionYear()));
		});
		
		return result;
	}
}
