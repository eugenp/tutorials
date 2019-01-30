package com.baeldung.projection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.SpringDataRestApplication;
import com.baeldung.models.Author;
import com.baeldung.models.Book;
import com.baeldung.repositories.AuthorRepository;
import com.baeldung.repositories.BookRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringDataRestApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)

public class SpringDataProjectionLiveTest {
    private static final String BOOK_ENDPOINT = "http://localhost:8080/books";
    private static final String AUTHOR_ENDPOINT = "http://localhost:8080/authors";

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private AuthorRepository authorRepo;

    @Before
    public void setup() {
        if (bookRepo.findById(1L) == null) {
            Book book = new Book("Animal Farm");
            book.setIsbn("978-1943138425");
            book = bookRepo.save(book);
            Author author = new Author("George Orwell");
            author = authorRepo.save(author);
            author.setBooks(Arrays.asList(book));
            author = authorRepo.save(author);
        }
    }

    @Test
    public void whenGetBook_thenOK() {
        final Response response = RestAssured.get(BOOK_ENDPOINT + "/1");

        assertEquals(200, response.getStatusCode());
        assertTrue(response.asString().contains("isbn"));
        assertFalse(response.asString().contains("authorCount"));
        // System.out.println(response.asString());
    }

    @Test
    public void whenGetBookProjection_thenOK() {
        final Response response = RestAssured.get(BOOK_ENDPOINT + "/1?projection=customBook");

        assertEquals(200, response.getStatusCode());
        assertFalse(response.asString().contains("isbn"));
        assertTrue(response.asString().contains("authorCount"));
        // System.out.println(response.asString());
    }

    @Test
    public void whenGetAllBooks_thenOK() {
        final Response response = RestAssured.get(BOOK_ENDPOINT);

        assertEquals(200, response.getStatusCode());
        assertFalse(response.asString().contains("isbn"));
        assertTrue(response.asString().contains("authorCount"));
        // System.out.println(response.asString());
    }

    @Test
    public void whenGetAuthorBooks_thenOK() {
        final Response response = RestAssured.get(AUTHOR_ENDPOINT + "/1/books");

        assertEquals(200, response.getStatusCode());
        assertFalse(response.asString().contains("isbn"));
        assertTrue(response.asString().contains("authorCount"));
        System.out.println(response.asString());
    }
}
