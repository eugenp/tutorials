package com.baeldung.hexa;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexa.movie.domain.MovieDTO;
import com.baeldung.hexa.movie.domain.MovieService;

@RestController
public class MovieController {

	MovieService service;
	
	public MovieController(MovieService service) {
		this.service = service;
	}

	@RequestMapping(value = "/movies", method = RequestMethod.POST)
	void addMovie(@RequestBody MovieDTO movie) {
		service.addMovie(movie);
	}
	
	@RequestMapping("/movies/search")
	MovieDTO findMovie(@RequestParam(required = true, name = "title") String title) {
		return service.findMovie(title);
	}
	
	@RequestMapping("/movies")
	List<MovieDTO> listMovies() {
		return service.listMovies();
	}
}
