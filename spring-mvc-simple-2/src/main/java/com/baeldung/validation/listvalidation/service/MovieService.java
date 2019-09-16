package com.baeldung.validation.listvalidation.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baeldung.validation.listvalidation.model.Movie;

@Service
public class MovieService {

    private final Logger logger = LoggerFactory.getLogger(MovieService.class);

    private static List<Movie> moviesData;

    static {
        moviesData = new ArrayList<>();

        Movie m1 = new Movie("MovieABC");
        moviesData.add(m1);

        Movie m2 = new Movie("MovieDEF");
        moviesData.add(m2);

    }
    
    public Movie get(String name) {
        Movie movie = null;
        for (Movie m : moviesData) {
            if (name.equalsIgnoreCase(m.getName())) {
                movie = m;
                logger.info("Found movie with name {} : {} ", name, movie);
            }
        }

        return movie;
    }

    public void add(Movie movie) {
        if (get(movie.getName()) == null) {
            moviesData.add(movie);
            logger.info("Added new movie - {}", movie.getName());
        }
    }

    public void addAll(List<Movie> movies) {
        for (Movie movie : movies) {
            add(movie);
        }
    }

}
