package com.baeldung.hexagonal.architecture.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.hexagonal.architecture.model.Movie;
import com.baeldung.hexagonal.architecture.port.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;


//@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(MovieController.class)
public class MovieControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;
    
    @MockBean
    MovieService movieService;
    
    @Test
    public void givenMovies_whenGetMovies_thenReturnJsonArray() throws Exception {
        Movie movie = new Movie(1, "The Predator",
            "https://www.youtube.com/watch?v=WaG1KZqrLvM",
            "From the outer reaches of space to the small-town streets of suburbia,"
            + " the hunt comes home in Shane Blacks explosive reinvention of the Predator"
            + " series. Now, the universes most lethal hunters are stronger,"
            + " smarter and deadlier than ever before,"
            + " having genetically upgraded themselves with DNA from other species."
            + " When a young boy accidentally triggers their return to Earth,"
            + " only a ragtag crew of ex-soldiers and a disgruntled science teacher"
            + " can prevent the end of the human race.", "https://d3ltpb4h29tx4j.cloudfront.net/227902/227902h1.jpg", 180);

        Movie movie1 = new Movie(2, "Avengers",
            "https://www.youtube.com/watch?v=WaG1KZqrLvM",
            "The Avengers Movie", "https://d3ltpb4h29tx4j.cloudfront.net/227902/227902h1.jpg", 120);

        Movie movie2 = new Movie(3, "Star Wars",
            "https://www.youtube.com/watch?v=WaG1KZqrLvM",
            "Star Wars", "https://d3ltpb4h29tx4j.cloudfront.net/227902/227902h1.jpg", 120);

        List<Movie> movies = Arrays.asList(movie, movie1, movie2);

        given(movieService.getMovies()).willReturn(movies);

        mvc.perform(get("/api/movie/showAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].movie_name", is(movie.getMovieName())));

        verify(movieService, VerificationModeFactory.times(1)).getMovies();
        reset(movieService);
    }

    @Test
    public void givenMovie_whenGetMovieById_thenReturnValidMovie() throws Exception {
        Movie movie = new Movie(1, "The Predator",
            "https://www.youtube.com/watch?v=WaG1KZqrLvM",
            "From the outer reaches of space to the small-town streets of suburbia,"
            + " the hunt comes home in Shane Blacks explosive reinvention of the Predator"
            + " series. Now, the universes most lethal hunters are stronger,"
            + " smarter and deadlier than ever before,"
            + " having genetically upgraded themselves with DNA from other species."
            + " When a young boy accidentally triggers their return to Earth,"
            + " only a ragtag crew of ex-soldiers and a disgruntled science teacher"
            + " can prevent the end of the human race.", "https://d3ltpb4h29tx4j.cloudfront.net/227902/227902h1.jpg", 180);

        given(movieService.getMovieById(Mockito.anyInt())).willReturn(movie);

        mvc.perform(get("/api/movie/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movie_name", is(movie.getMovieName())));

        verify(movieService, VerificationModeFactory.times(1)).getMovieById(Mockito.anyInt());
        reset(movieService);
    }

    @Test
    public void whenPostMovie_thenCreateMovie() throws Exception {
        Movie movie = new Movie(1, "The Predator",
            "https://www.youtube.com/watch?v=WaG1KZqrLvM",
            "From the outer reaches of space to the small-town streets of suburbia,"
            + " the hunt comes home in Shane Blacks explosive reinvention of the Predator"
            + " series. Now, the universes most lethal hunters are stronger,"
            + " smarter and deadlier than ever before,"
            + " having genetically upgraded themselves with DNA from other species."
            + " When a young boy accidentally triggers their return to Earth,"
            + " only a ragtag crew of ex-soldiers and a disgruntled science teacher"
            + " can prevent the end of the human race.", "https://d3ltpb4h29tx4j.cloudfront.net/227902/227902h1.jpg", 180);
        given(movieService.addMovie(Mockito.any())).willReturn(movie);

        mvc.perform(post("/api/movie/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(movie))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(movieService, VerificationModeFactory.times(1)).addMovie(Mockito.any());
        reset(movieService);
    }

    @Test
    public void whenDeleteProduct_thenRemoveValidProduct() throws Exception {
        Movie movie = new Movie(1, "The Predator",
            "https://www.youtube.com/watch?v=WaG1KZqrLvM",
            "From the outer reaches of space to the small-town streets of suburbia,"
            + " the hunt comes home in Shane Blacks explosive reinvention of the Predator"
            + " series. Now, the universes most lethal hunters are stronger,"
            + " smarter and deadlier than ever before,"
            + " having genetically upgraded themselves with DNA from other species."
            + " When a young boy accidentally triggers their return to Earth,"
            + " only a ragtag crew of ex-soldiers and a disgruntled science teacher"
            + " can prevent the end of the human race.", "https://d3ltpb4h29tx4j.cloudfront.net/227902/227902h1.jpg", 180);
        
        given(movieService.removeMovie(Mockito.anyInt())).willReturn(movie);

        mvc.perform(delete("/api/movie/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movie_name", is(movie.getMovieName())));

        verify(movieService, VerificationModeFactory.times(1)).removeMovie(Mockito.anyInt());
        reset(movieService);
    }
}
