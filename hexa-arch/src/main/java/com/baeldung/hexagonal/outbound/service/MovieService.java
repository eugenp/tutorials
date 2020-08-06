package com.baeldung.hexagonal.outbound.service;

import com.baeldung.hexagonal.model.Movie;
import com.baeldung.hexagonal.outbound.repo.MovieDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface MovieService {

    public List<Movie> getMovies();

    public void addMovie(Movie movie);
}
