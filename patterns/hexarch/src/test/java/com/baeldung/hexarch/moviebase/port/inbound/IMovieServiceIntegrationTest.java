package com.baeldung.hexarch.moviebase.port.inbound;

import com.baeldung.hexarch.moviebase.adapter.secondary.MovieRepository;
import com.baeldung.hexarch.moviebase.domain.model.Genre;
import com.baeldung.hexarch.moviebase.domain.model.Movie;
import com.baeldung.hexarch.moviebase.domain.model.Person;
import com.baeldung.hexarch.moviebase.domain.service.MovieService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class IMovieServiceIntegrationTest {

    @TestConfiguration
    static class MovieServiceTestConfig {
        @Bean
        public IMovieService movieService() {
            return new MovieService();
        }
    }


    @MockBean
    private MovieRepository movieRepository;

    @Autowired
    private MovieService movieService;

    @Before
    public void setUp() {

        Movie movie1 =
            new Movie(
                "abe0e9607-022b-1bd0-a9fa-42b3c90740f0",
                "Gone with the Wind",
                "en",
                1939,
                "USA",
                Genre.ROMANCE,
                "Love in the age of an American Civil War",
                14280,
                8.1,
                new Person("Victor Fleming"),
                new Person[] {new Person("Clark Gable"), new Person("Vivien Leigh")});

        Movie movie2 =
            new Movie(
                "61782507-9b2b-0c00-a411-aaba10074b1a",
                "The Avengers",
                "en",
                2012,
                "USA",
                Genre.ACTION,
                "Earth's mightiest heroes fight as a team to save the planet Earth",
                8580,
                8.0,
                new Person("Joss Whedon"),
                new Person[] {new Person("Chris Evans"), new Person("Scarlett Johansson")});

        Movie movie3 = new Movie(
            "a19925ba-ab0a-90a1-9180-a9a910bbbb1a",
            "Escape Room: Tournament of Champions",
            "en",
            2021,
            "USA",
            Genre.THRILLER,
            "Winners of the last tournament try to find the exit from the new escape room",
            8580,
            6.1,
            new Person("Adam Robitel"),
            new Person[] {new Person("Taylor Russell"), new Person("Logan Miller")});

        List<Movie> movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);

        Mockito.when(movieRepository.getMovies()).thenReturn(movies);
        Mockito.when(movieRepository.getMovie("a19925ba-ab0a-90a1-9180-a9a910bbbb1a"))
            .thenReturn(movie3);

    }

    @Test
    public void givenId_whenGivenValidUuid_thenMovieReturned() {
        String uuid = "a19925ba-ab0a-90a1-9180-a9a910bbbb1a";
        Movie movie = movieService.getMovie(uuid);

        Assertions.assertEquals(movie.getId(), uuid);

        Mockito.verify(movieRepository, VerificationModeFactory.times(1)).getMovie(uuid);
        Mockito.reset(movieRepository);
    }

    @Test
    public void givenMovies_whenGetMovies_thenMoviesReturned() {

        Movie movie1 =
            new Movie(
                "abe0e9607-022b-1bd0-a9fa-42b3c90740f0",
                "Gone with the Wind",
                "en",
                1939,
                "USA",
                Genre.ROMANCE,
                "Love in the age of an American Civil War",
                14280,
                8.1,
                new Person("Victor Fleming"),
                new Person[] {new Person("Clark Gable"), new Person("Vivien Leigh")});

        Movie movie2 =
            new Movie(
                "61782507-9b2b-0c00-a411-aaba10074b1a",
                "The Avengers",
                "en",
                2012,
                "USA",
                Genre.ACTION,
                "Earth's mightiest heroes fight as a team to save the planet Earth",
                8580,
                8.0,
                new Person("Joss Whedon"),
                new Person[] {new Person("Chris Evans"), new Person("Scarlett Johansson")});

        Movie movie3 = new Movie(
            "a19925ba-ab0a-90a1-9180-a9a910bbbb1a",
            "Escape Room: Tournament of Champions",
            "en",
            2021,
            "USA",
            Genre.THRILLER,
            "Winners of the last tournament try to find the exit from the new escape room",
            8580,
            6.1,
            new Person("Adam Robitel"),
            new Person[] {new Person("Taylor Russell"), new Person("Logan Miller")});

        List<Movie> movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);

        List<Movie> movieList = movieService.getMovies();

        Assertions.assertEquals(movieList.size(), movies.size());
        Assertions.assertEquals(movieList.get(0).getId(), movie1.getId());
        Assertions.assertEquals(movieList.get(1).getId(), movie2.getId());
        Assertions.assertEquals(movieList.get(2).getId(), movie3.getId());

        Mockito.verify(movieRepository, VerificationModeFactory.times(1)).getMovies();
        Mockito.reset(movieRepository);
    }
}
