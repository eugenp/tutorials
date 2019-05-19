package com.baeldung.hexagonal.application.port.input;

import com.baeldung.hexagonal.application.port.output.FindMovies;
import com.baeldung.hexagonal.application.port.output.FindUser;
import com.baeldung.hexagonal.application.service.MovieService;
import com.baeldung.hexagonal.domain.Movie;
import com.baeldung.hexagonal.domain.User;
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

        User user = new User("Bruce Wayne", Movie.Genre.COMEDY);
        Set<Movie> comedyMovies = new HashSet<>();
        comedyMovies.add(new Movie("Deadpool", Movie.Genre.COMEDY));

        when(findUser.byName("Bruce Wayne")).thenReturn(user);
        when(findMovies.byGenre(Movie.Genre.COMEDY)).thenReturn(comedyMovies);

        Set<Movie> movies = suggestMovies.toUserByName("Bruce Wayne");

        assertIterableEquals(comedyMovies, movies);
    }
}
