package com.baeldung.hexagonal.example.test.helper;

import com.baeldung.hexagonal.example.domain.entity.Movie;

import java.util.HashSet;
import java.util.Random;

import static java.util.Collections.singletonList;

public class TestUtility {
    public static String randomString(int length) {
        byte[] bytes = new byte[length];
        new Random().nextBytes(bytes);
        return new String(bytes);
    }

    public static Movie testMovie(String value) {
        Movie movie = new Movie();
        movie.setTitle(value);
        movie.setActors(singletonList(value));
        movie.setDirector(value);
        movie.setReviews(new HashSet<>());
        return movie;
    }
}
