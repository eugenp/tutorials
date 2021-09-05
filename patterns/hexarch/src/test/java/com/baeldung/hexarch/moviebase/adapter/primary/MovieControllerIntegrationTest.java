package com.baeldung.hexarch.moviebase.adapter.primary;

import com.baeldung.hexarch.moviebase.domain.model.Genre;
import com.baeldung.hexarch.moviebase.domain.model.Movie;
import com.baeldung.hexarch.moviebase.domain.model.Person;
import com.baeldung.hexarch.moviebase.domain.objects.MethodResult;
import com.baeldung.hexarch.moviebase.port.inbound.IMovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
public class MovieControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private IMovieService movieService;

    @Test
    public void givenMovies_whenGetMovies_thenReturnExpectedArray() throws Exception {
        Movie movie1 = new Movie(
                "abe0e9607-022b-1bd0-a9fa-42b3c90740f0",
                "Gone with the Wind",
                "en",
                1939,
                "USA",
                Genre.ROMANCE,
                "A manipulative woman and a roguish man conduct a turbulent romance during the American Civil War and Reconstruction periods.",
                14280,
                8.1,
                new Person("Victor Fleming"),
                new Person[]{new Person("Clark Gable"), new Person("Vivien Leigh")});

        Movie movie2 = new Movie(
                "61782507-9b2b-0c00-a411-aaba10074b1a",
                "The Avengers",
                "en",
                2012,
                "USA",
                Genre.ACTION,
                "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.",
                8580,
                8.0,
                new Person("Joss Whedon"),
                new Person[]{new Person("Chris Evans"), new Person("Scarlett Johansson"), new Person("Chris Hemsworth")});

        Movie movie3 = new Movie(
                "a19925ba-ab0a-90a1-9180-a9a910bbbb1a",
                "Escape Room: Tournament of Champions",
                "en",
                2021,
                "USA",
                Genre.THRILLER,
                "A manipulative woman and a roguish man conduct a turbulent romance during the American Civil War and Reconstruction periods.",
                8580,
                6.1,
                new Person("Adam Robitel"),
                new Person[]{new Person("Taylor Russell"), new Person("Logan Miller"), new Person("Deborah Woll")});

        List<Movie> movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);

        given(movieService.getMovies()).willReturn(movies);

        mvc.perform(get("/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].title", is(movie1.getTitle())))
                .andExpect(jsonPath("$[0].language", is(movie1.getLanguage())));

        verify(movieService, VerificationModeFactory.times(1)).getMovies();
        reset(movieService);
    }

    @Test
    public void givenMovies_whenGetMovie_thenReturnExpectedMovie() throws Exception {

        Movie movie1 = new Movie(
                "abe0e9607-022b-1bd0-a9fa-42b3c90740f0",
                "Gone with the Wind",
                "en",
                1939,
                "USA",
                Genre.ROMANCE,
                "A manipulative woman and a roguish man conduct a turbulent romance during the American Civil War and Reconstruction periods.",
                14280,
                8.1,
                new Person("Victor Fleming"),
                new Person[]{new Person("Clark Gable"), new Person("Vivien Leigh")});

        Movie movie2 = new Movie(
                "61782507-9b2b-0c00-a411-aaba10074b1a",
                "The Avengers",
                "en",
                2012,
                "USA",
                Genre.ACTION,
                "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.",
                8580,
                8.0,
                new Person("Joss Whedon"),
                new Person[]{new Person("Chris Evans"), new Person("Scarlett Johansson"), new Person("Chris Hemsworth")});

        Movie movie3 = new Movie(
                "a19925ba-ab0a-90a1-9180-a9a910bbbb1a",
                "Escape Room: Tournament of Champions",
                "en",
                2021,
                "USA",
                Genre.THRILLER,
                "A manipulative woman and a roguish man conduct a turbulent romance during the American Civil War and Reconstruction periods.",
                8580,
                6.1,
                new Person("Adam Robitel"),
                new Person[]{new Person("Taylor Russell"), new Person("Logan Miller"), new Person("Deborah Woll")});

        List<Movie> movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);

        given(movieService.getMovie("a19925ba-ab0a-90a1-9180-a9a910bbbb1a")).willReturn(movie3);

        mvc.perform(get("/movies/a19925ba-ab0a-90a1-9180-a9a910bbbb1a")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(movie3.getTitle())));

        verify(movieService, VerificationModeFactory.times(1)).getMovie("a19925ba-ab0a-90a1-9180-a9a910bbbb1a");
        reset(movieService);
    }

    @Test
    public void givenMovies_whenDeleteMovie_thenMovieDeleted() throws Exception {

        given(movieService.deleteMovie("61782507-9b2b-0c00-a411-aaba10074b1a")).willReturn(MethodResult.SUCCESS.name());

        mvc.perform(delete("/movies/61782507-9b2b-0c00-a411-aaba10074b1a")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(movieService, VerificationModeFactory.times(1)).deleteMovie("61782507-9b2b-0c00-a411-aaba10074b1a");
        reset(movieService);
    }

}