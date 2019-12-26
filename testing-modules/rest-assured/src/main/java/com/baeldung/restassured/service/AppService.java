package com.baeldung.restassured.service;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.baeldung.restassured.model.Movie;

@Service
public class AppService {

    private Set<Movie> movieSet = new HashSet<>();

    public Set<Movie> getAll() {
        return movieSet;
    }

    public void add(Movie movie) {
        movieSet.add(movie);
    }

    public Movie findMovie(int id) {
        return movieSet.stream()
            .filter(movie -> movie.getId()
                .equals(id))
            .findFirst()
            .orElse(null);
    }

    public File getFile(int id) {
        File file = null;
        try {
            file = new ClassPathResource(String.valueOf(id)).getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

}
