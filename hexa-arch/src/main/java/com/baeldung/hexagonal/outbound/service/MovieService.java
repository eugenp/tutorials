package com.baeldung.hexagonal.outbound.service;

import com.baeldung.hexagonal.model.Movie;
import com.baeldung.hexagonal.outbound.repo.MovieDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    MovieDao movieDao;


    public List<Movie> getMovies() {
        //could have more business Logic
        return movieDao.getAllMovies();
    }


    public void addMovie(Movie movie) {
        //could have more business Logic
        movieDao.addNewMovie(movie);
    }
}
