package com.baeldung.injection;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MovieFinder {

    public List<Movie> find(Long userId) {
        return Arrays.asList(new Movie("The Godfather", "Francis Ford Copolla"), new Movie("Batman Begins", "Christopher Nolan"), new Movie("Lord of the Rings", "Peter Jackson"));
    }

}
