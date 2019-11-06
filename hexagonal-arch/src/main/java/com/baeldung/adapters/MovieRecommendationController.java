package com.baeldung.adapters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.ports.MovieRecommendationService;
import com.baeldung.vo.Movie;

/**
 * Primary Adapter
 * @author rchaudhary23
 *
 */
@RestController
public class MovieRecommendationController {
	
	@Autowired
	private MovieRecommendationService service;
	
	@RequestMapping(value="/movies")
	public List<Movie> findMovieByReleaseYear(@RequestParam(required = false) String year, 
			@RequestParam(required = false) String genre){
		
		if(year==null && genre==null) return null;
		
		if(year!=null && genre!=null) {
			return service.findByGenreAndYearOfRelease(year, genre);
		}else if(year!=null) {
			return service.findByYearOfRelease(year);
		}else {
			return service.findByGenre(genre);
		}
	}
}
