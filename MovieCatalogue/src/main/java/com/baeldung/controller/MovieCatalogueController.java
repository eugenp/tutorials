package com.baeldung.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.model.MovieCatalogue;
import com.baeldung.service.MovieCatalogueService;
//input Adapter
@RestController
public class MovieCatalogueController {
	
	@Autowired
	private MovieCatalogueService movieCatalogueService;
	
	public MovieCatalogueController() {
		
	}
	
	public MovieCatalogueController(MovieCatalogueService movieCatalogueService) {
		this.movieCatalogueService = movieCatalogueService; 
		
	}
	
	@GetMapping ("/allMovies")
	public List<MovieCatalogue> getAllMovies() {
		
		return movieCatalogueService.getAllMovies();
	}
	
	
}
