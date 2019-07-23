package com.baeldung.validation.listvalidation.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.baeldung.validation.listvalidation.model.Actor;
import com.baeldung.validation.listvalidation.model.Movie;

@Repository
public class MovieRepository {
    private final Logger LOGGER = LoggerFactory.getLogger(MovieRepository.class);
    static List<Movie> moviesData;

    static {
        moviesData = new ArrayList<>();

        Actor a1 = new Actor("Actor1");

        Movie m1 = new Movie("MovieABC", Arrays.asList("Drama"), Arrays.asList(a1));
        moviesData.add(m1);

        Movie m2 = new Movie("MovieDEF", Arrays.asList("Drama"), Arrays.asList(a1));
        moviesData.add(m2);

    }

    public void add(Movie movie) {
        if (get(movie.getName()) == null) {
            moviesData.add(movie);
            LOGGER.info("Added new movie.");
        }
    }

    public Movie get(String name) {
        Movie movie = null;
        for (Movie m : moviesData) {
            if (name.equalsIgnoreCase(m.getName())) {
                movie = m;
                LOGGER.info("Found movie with name " + name + ":" + movie);
            }
        }

        return movie;
    }

    public void addAll(List<Movie> movies) {
        for (Movie movie : movies) {
            add(movie);
        }
    }

}
