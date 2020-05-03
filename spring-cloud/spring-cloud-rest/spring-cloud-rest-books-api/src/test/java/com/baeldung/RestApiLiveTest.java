package com.baeldung;

import static io.restassured.RestAssured.preemptive;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.baeldung.BooksApiApplication;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import com.baeldung.persistence.model.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { BooksApiApplication.class }, webEnvironment = WebEnvironment.DEFINED_PORT)
public class RestApiLiveTest {

    private static final String API_URI = "http://localhost:8084/books";

    @Before
    public void setUp() {
        RestAssured.authentication = preemptive().basic("user", "userPass");
    }

    // GET

    @Test
    public void whenGetAllBooks_thenOK() {
        final Response response = RestAssured.get(API_URI);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetCreatedBookById_thenOK() {
        final Book book = createRandomBook();
        final String location = createBookAsUri(book);

        final Response response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(book.getTitle(), response.jsonPath().get("title"));
    }

    @Test
    public void whenGetCreatedBookByName_thenOK() {
        final Book book = createRandomBook();
        createBookAsUri(book);

        final Response response = RestAssured.get(API_URI + "/search/findByTitle?title=" + book.getTitle());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.jsonPath().getLong("page.totalElements") > 0);
    }

    @Test
    public void whenGetNotExistBookById_thenNotFound() {
        final Response response = RestAssured.get(API_URI + "/" + randomNumeric(4));
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenGetNotExistBookByName_thenNotFound() {
        final Response response = RestAssured.get(API_URI + "/search/findByTitle?title=" + randomAlphabetic(20));
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.jsonPath().getLong("page.totalElements") == 0);
    }

    // POST
    @Test
    public void whenCreateNewBook_thenCreated() {
        final Book book = createRandomBook();

        final Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(book).post(API_URI);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenCreateDuplicateBook_thenError() {
        final Book book = createRandomBook();
        createBookAsUri(book);

        // duplicate
        final Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(book).post(API_URI);
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidBook_thenError() {
        final Book book = createRandomBook();
        book.setAuthor(null);

        final Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(book).post(API_URI);
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedBook_thenUpdated() {
        // create
        final Book book = createRandomBook();
        final String location = createBookAsUri(book);

        // update
        book.setAuthor("newAuthor");
        Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(book).put(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        // check if changes saved
        response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("newAuthor", response.jsonPath().get("author"));

    }

    @Test
    public void whenDeleteCreatedBook_thenOk() {
        // create
        final Book book = createRandomBook();
        final String location = createBookAsUri(book);

        // delete
        Response response = RestAssured.delete(location);
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());

        // confirm it was deleted
        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenDeleteNotExistBook_thenError() {
        final Response response = RestAssured.delete(API_URI + "/" + randomNumeric(4));
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // =============================== Util

    private Book createRandomBook() {
        final Book book = new Book();
        book.setTitle(randomAlphabetic(10));
        book.setAuthor(randomAlphabetic(15));
        return book;
    }

    private String createBookAsUri(Book book) {
        final Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(book).post(API_URI);
        return response.jsonPath().get("_links.self.href");
    }

}
