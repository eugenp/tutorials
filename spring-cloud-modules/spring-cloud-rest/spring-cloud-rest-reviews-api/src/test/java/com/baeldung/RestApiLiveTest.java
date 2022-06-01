package com.baeldung;

import static io.restassured.RestAssured.preemptive;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.baeldung.BookReviewsApiApplication;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import com.baeldung.persistence.model.BookReview;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { BookReviewsApiApplication.class }, webEnvironment = WebEnvironment.DEFINED_PORT)
public class RestApiLiveTest {

    private static final String API_URI = "http://localhost:8085/reviews";

    @Before
    public void setUp() {
        RestAssured.authentication = preemptive().basic("user", "userPass");
    }

    // GET

    @Test
    public void whenGetAllReviews_thenOK() {
        final Response response = RestAssured.get(API_URI);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetCreatedReviewById_thenOK() {
        final BookReview review = createRandomReview();
        final String location = createReviewAsUri(review);

        final Response response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(review.getContent(), response.jsonPath().get("content"));
    }

    @Test
    public void whenGetCreatedReviewByBookId_thenOK() {
        final BookReview review = createRandomReview();
        createReviewAsUri(review);

        final Response response = RestAssured.get(API_URI + "/search/findByBookId?bookId=" + review.getBookId());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.jsonPath().getLong("page.totalElements") > 0);
    }

    @Test
    public void whenGetNotExistReviewById_thenNotFound() {
        final Response response = RestAssured.get(API_URI + "/" + randomNumeric(4));
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenGetNotExistReviewByBookId_thenNotFound() {
        final Response response = RestAssured.get(API_URI + "/search/findByBookId?bookId=" + randomNumeric(4));
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.jsonPath().getLong("page.totalElements") == 0);
    }

    // POST
    @Test
    public void whenCreateNewReview_thenCreated() {
        final BookReview review = createRandomReview();

        final Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(review).post(API_URI);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenCreateInvalidReview_thenError() {
        final BookReview review = createRandomReview();
        review.setBookId(null);

        final Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(review).post(API_URI);
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedReview_thenUpdated() {
        // create
        final BookReview review = createRandomReview();
        final String location = createReviewAsUri(review);

        // update
        review.setRating(4);
        Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(review).put(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        // check if changes saved
        response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(4, response.jsonPath().getInt("rating"));

    }

    @Test
    public void whenDeleteCreatedReview_thenOk() {
        // create
        final BookReview review = createRandomReview();
        final String location = createReviewAsUri(review);

        // delete
        Response response = RestAssured.delete(location);
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());

        // confirm it was deleted
        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenDeleteNotExistReview_thenError() {
        final Response response = RestAssured.delete(API_URI + "/" + randomNumeric(4));
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // =============================== Util

    private BookReview createRandomReview() {
        final BookReview review = new BookReview();
        review.setContent(randomAlphabetic(10));
        review.setRating(3);
        review.setBookId(1L);
        return review;
    }

    private String createReviewAsUri(BookReview review) {
        final Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(review).post(API_URI);
        return response.jsonPath().get("_links.self.href");
    }

}
