package com.baeldung.hexagonal.outbound.repo;


import com.baeldung.hexagonal.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieDaoImpl implements MovieDao {

    @Autowired
    MovieRepo movieRepo;


    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        movieRepo.findAll().forEach(movies::add);
        return movies;
    }

    @Override
    public void addNewMovie(Movie movie) {
        movieRepo.save(movie);
    }

}
