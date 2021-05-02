package com.baeldung.dao;

import java.util.List;

import com.baeldung.model.MovieCatalogue;
//output port
public interface MovieDao {
	
List<MovieCatalogue> getAllMovies(); 

}
