package com.baeldung.hexagonal.example.integration;

import com.baeldung.hexagonal.example.domain.entity.Movie;
import com.baeldung.hexagonal.example.domain.entity.Review;
import com.baeldung.hexagonal.example.test.helper.MockDatabaseAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.baeldung.hexagonal.example.domain.entity.Review.Rating.FIVE_STAR;
import static com.baeldung.hexagonal.example.test.helper.TestUtility.testMovie;
import static java.util.Collections.singletonList;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = IntegrationTestConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
class MovieReviewIntegrationTest {
    private static final String BASE_URI  ="/api/v1/movie";

    MockMvc mvc;
    MockDatabaseAdapter mockDB;
    ObjectMapper mapper;

    public MovieReviewIntegrationTest(
            WebApplicationContext wac, @Autowired MockDatabaseAdapter mockDB) {
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.mockDB = mockDB;
        this.mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Given a movie stored in the review system, when we call the REST endpoint for 'title', then movie is returned.")
    void givenAMovieStoredInSystem_whenICallTheRESTEndpointForMovieByTitle_thenMovieIsReturned() throws Exception {
        String randomMovieTitle = randomUUID().toString();
        mockDB.setMockEntry(testMovie(randomMovieTitle));

        MockHttpServletResponse response = mvc.perform(get(BASE_URI + "/title/" + randomMovieTitle))
          .andDo(print())
          .andExpect(status().isOk())
          .andReturn()
          .getResponse();

        Movie movie = mapper.readValue(response.getContentAsString(), Movie.class);

        assertThat(movie)
          .extracting("title")
          .isEqualTo(randomMovieTitle);
    }

    @Test
    @DisplayName("Given a movie stored in the review system, when we call the REST endpoint for 'director', then movie is returned.")
    void givenAMovieStoredInSystem_whenICallTheRESTEndpointForMovieByDirector_thenMovieIsReturned() throws Exception {
        String randomMovieDirector = randomUUID().toString();
        mockDB.setMockEntry(testMovie(randomMovieDirector));

        MockHttpServletResponse response = mvc.perform(get(BASE_URI + "/director/" + randomMovieDirector))
          .andDo(print())
          .andExpect(status().isOk())
          .andReturn()
          .getResponse();

        Movie[] movie = mapper.readValue(response.getContentAsString(), Movie[].class);

        assertThat(movie)
          .extracting("director")
          .isEqualTo(singletonList(randomMovieDirector));
    }

    @Test
    @DisplayName("Given a movie stored in the review system, when we call the REST endpoint for 'actor', then movie is returned.")
    void givenAMovieStoredInSystem_whenICallTheRESTEndpointForMovieByActor_thenMovieIsReturned() throws Exception {
        String randomMovieActor = randomUUID().toString();
        mockDB.setMockEntry(testMovie(randomMovieActor));

        MockHttpServletResponse response = mvc.perform(get(BASE_URI + "/actor/" + randomMovieActor))
          .andDo(print())
          .andExpect(status().isOk())
          .andReturn()
          .getResponse();

        Movie[] movie = mapper.readValue(response.getContentAsString(), Movie[].class);

        assertThat(movie)
          .extracting("actors")
          .isEqualTo(singletonList(singletonList(randomMovieActor)));
    }

    @Test
    @DisplayName("Given a movie stored in the review system, when we add a review, then we can view the review in the movie's review list.")
    void givenAMovieExistsInSystem_whenIAddAReview_thenICanSeeTheReviewInTheMoviesReviewList() throws Exception {
        String randomMovieTitle = randomUUID().toString();
        Movie testMovie = testMovie(randomMovieTitle);
        mockDB.setMockEntry(testMovie);

        Review review = new Review();
        review.setRating(FIVE_STAR);

        mvc.perform(post(BASE_URI + "/review/" + randomMovieTitle)
          .contentType(MediaType.APPLICATION_JSON)
          .content(mapper.writeValueAsString(review)))
          .andDo(print())
          .andExpect(status().isOk());

        Review result = testMovie.getReviews().iterator().next();

        assertThat(result)
          .extracting("rating").isEqualTo(FIVE_STAR);
    }
}
