package com.baeldung.hexagonal.library.controller;

import com.baeldung.hexagonal.library.LibraryTestConfig;
import com.baeldung.hexagonal.library.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import(LibraryTestConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenTitleIsProvidedForSearch_thenBooksWithThatTitleAreReturned() {
        Book[] books = restTemplate.getForObject("http://localhost:" + port + "/books?title=gambler", Book[].class);

        assertThat(books.length).isEqualTo(1);

        Book firstBook = books[0];
        assertThat(firstBook)
                .extracting(Book::getTitle)
                .isEqualTo("The Gambler");
    }
}