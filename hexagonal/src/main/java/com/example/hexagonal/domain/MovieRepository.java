package com.example.hexagonal.domain;

import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long>{
	Movie findMovieByTitle(String name);	
}
