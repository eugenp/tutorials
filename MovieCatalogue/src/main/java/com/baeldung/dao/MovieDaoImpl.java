package com.baeldung.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.model.MovieCatalogue;
//output adapter 
@Repository
public class MovieDaoImpl implements MovieDao {
	   private Map<String, MovieCatalogue> movies = new HashMap<String, MovieCatalogue>();
	
	MovieDaoImpl(){
		
		movies.put("First Movie", new MovieCatalogue("Games Of Thrones", 1, "Alan Taylor", 9.3));
		movies.put("Second Movie", new MovieCatalogue("The Avnegers", 2, "Anthony Russo", 8));
		movies.put("Third Movie", new MovieCatalogue("Inception",  3, "Christopher Nolan", 8.8));
	}
	
	@Override
	public List<MovieCatalogue> getAllMovies() {
		
		return movies.values().stream().collect(Collectors.toList());
	}

	
	

}
