package com.baeldung.hexagonal.application.port.input;

import hexagonal.application.port.input.SuggestMovies;
import hexagonal.application.port.output.FindMovies;
import hexagonal.application.port.output.FindUser;
import hexagonal.application.service.MovieService;
import hexagonal.domain.Movie;
import hexagonal.domain.User;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SuggestMoviesUnitTest {
    @Test
    void whenSuggestingMoviesToUser_thenGetMoviesByUserPreferredGenre() {
        FindUser findUser = mock(FindUser.class);
        FindMovies findMovies = mock(FindMovies.class);
        SuggestMovies suggestMovies = new MovieService(findUser, findMovies);

        User user = new User("Bruce Wayne", "Comedy");
        Set<Movie> comedyMovies = new HashSet<>();
        comedyMovies.add(new Movie("Deadpool", "Comedy"));

        when(findUser.byName("Bruce Wayne")).thenReturn(user);
        when(findMovies.byGenre("Comedy")).thenReturn(comedyMovies);

        Set<Movie> movies = suggestMovies.toUserByName("Bruce Wayne");

        assertIterableEquals(comedyMovies, movies);
    }
}
