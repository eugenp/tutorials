package com.baeldung.restassured.controller;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.restassured.model.Movie;
import com.baeldung.restassured.service.AppService;

import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AppControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @MockBean
    AppService appService;

    @Test
    public void givenMovieId_whenMakingGetRequestToMovieEndpoint_thenReturnMovie() {

        Movie testMovie = new Movie(1, "movie1", "summary1");
        when(appService.findMovie(1)).thenReturn(testMovie);

        get(uri + "/movie/" + testMovie.getId()).then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .body("id", equalTo(testMovie.getId()))
            .body("name", equalTo(testMovie.getName()))
            .body("synopsis", notNullValue());

        Movie result = get(uri + "/movie/" + testMovie.getId()).then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(Movie.class);
        assertThat(result).isEqualTo(testMovie);

        String responseString = get(uri + "/movie/" + testMovie.getId()).then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .asString();
        assertThat(responseString).isNotEmpty();
    }

    @Test
    public void whenCallingMoviesEndpoint_thenReturnAllMovies() {

        Set<Movie> movieSet = new HashSet<>();
        movieSet.add(new Movie(1, "movie1", "summary1"));
        movieSet.add(new Movie(2, "movie2", "summary2"));
        when(appService.getAll()).thenReturn(movieSet);

        get(uri + "/movies").then()
            .statusCode(HttpStatus.OK.value())
            .assertThat()
            .body("size()", is(2));

        Movie[] movies = get(uri + "/movies").then()
            .statusCode(200)
            .extract()
            .as(Movie[].class);
        assertThat(movies.length).isEqualTo(2);
    }

    @Test
    public void givenMovie_whenMakingPostRequestToMovieEndpoint_thenCorrect() {

        Map<String, String> request = new HashMap<>();
        request.put("id", "11");
        request.put("name", "movie1");
        request.put("synopsis", "summary1");

        int movieId = given().contentType("application/json")
            .body(request)
            .when()
            .post(uri + "/movie")
            .then()
            .assertThat()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .path("id");
        assertThat(movieId).isEqualTo(11);

    }

    @Test
    public void whenCallingWelcomeEndpoint_thenCorrect() {

        get(uri + "/welcome").then()
            .assertThat()
            .header("sessionId", notNullValue())
            .cookie("token", notNullValue());

        Response response = get(uri + "/welcome");

        String headerName = response.getHeader("sessionId");
        String cookieValue = response.getCookie("token");
        assertThat(headerName).isNotBlank();
        assertThat(cookieValue).isNotBlank();
    }

    @Test
    public void givenId_whenCallingDowloadEndpoint_thenCorrect() throws IOException {

        File file = new ClassPathResource("test.txt").getFile();
        long fileSize = file.length();
        when(appService.getFile(1)).thenReturn(file);

        byte[] result = get(uri + "/download/1").asByteArray();

        assertThat(result.length).isEqualTo(fileSize);
    }

}
