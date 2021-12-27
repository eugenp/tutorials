package com.baeldung.validation.listvalidation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.baeldung.validation.listvalidation.model.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringListValidationApplication.class)
@AutoConfigureMockMvc
public class MovieControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    ObjectMapper objectMapper = new ObjectMapper();
    
    @Test
    public void givenValidMovieList_whenAddingMovieList_thenIsOK() throws Exception {
        List<Movie> movies = new ArrayList<>();
        Movie movie = new Movie("Movie3");
        movies.add(movie);
        mvc.perform(MockMvcRequestBuilders.post("/movies")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(movies)))
            .andExpect(MockMvcResultMatchers.status()
                .isOk());
    }
    
    @Test
    public void givenEmptyMovieList_whenAddingMovieList_thenThrowBadRequest() throws Exception {
        List<Movie> movies = new ArrayList<>();
        mvc.perform(MockMvcRequestBuilders.post("/movies")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(movies)))
            .andExpect(MockMvcResultMatchers.status()
                .isBadRequest());
    }
    
    @Test
    public void givenEmptyMovieName_whenAddingMovieList_thenThrowBadRequest() throws Exception {
        Movie movie = new Movie("");
        mvc.perform(MockMvcRequestBuilders.post("/movies")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(Arrays.asList(movie))))
            .andExpect(MockMvcResultMatchers.status()
                .isBadRequest());
    }
    
    @Test
    public void given5MoviesInputList_whenAddingMovieList_thenThrowBadRequest() throws Exception {
        Movie movie1 = new Movie("Movie1");
        Movie movie2 = new Movie("Movie2");
        Movie movie3 = new Movie("Movie3");
        Movie movie4 = new Movie("Movie4");
        Movie movie5 = new Movie("Movie5");
        List<Movie> movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);
        movies.add(movie4);
        movies.add(movie5);
        mvc.perform(MockMvcRequestBuilders.post("/movies")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(movies)))
            .andExpect(MockMvcResultMatchers.status()
                .isBadRequest());
    }
    
}
