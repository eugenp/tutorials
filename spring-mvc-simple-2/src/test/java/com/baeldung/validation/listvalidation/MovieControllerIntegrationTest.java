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

import com.baeldung.validation.listvalidation.model.Actor;
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
    public void given1Genre_whenAddingMovie_thenThrowBadRequest() throws Exception {
        Actor actor = new Actor("Actor1");
        Movie movie = new Movie("Movie1", Arrays.asList("Drama"), Arrays.asList(actor));
        mvc.perform(MockMvcRequestBuilders.post("/movie")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(movie)))
            .andExpect(MockMvcResultMatchers.status()
                .isBadRequest());
    }

    @Test
    public void givenWithoutActor_whenAddingMovieList_thenThrowBadRequest() throws Exception {
        List<Actor> actors = new ArrayList<>();
        Movie movie = new Movie("Movie2", Arrays.asList("Action", "Thriller"), actors);
        mvc.perform(MockMvcRequestBuilders.post("/movie/batch")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(Arrays.asList(movie))))
            .andExpect(MockMvcResultMatchers.status()
                .isBadRequest());
    }

    @Test
    public void givenEmptyMovieName_whenAddingMovie_thenThrowBadRequest() throws Exception {
        Actor actor = new Actor("Actor1");
        Movie movie = new Movie("", Arrays.asList("Drama", "History"), Arrays.asList(actor));
        mvc.perform(MockMvcRequestBuilders.post("/movie")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(movie)))
            .andExpect(MockMvcResultMatchers.status()
                .isBadRequest());
    }

}
