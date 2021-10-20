package com.example.hexagonal.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.hexagonal.domain.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    Movie findMovieByTitle(String name);
}
