package com.baeldung.feign.clients;

import com.baeldung.feign.BookControllerFeignClientBuilder;
import com.baeldung.feign.models.Book;
import com.baeldung.feign.models.BookResource;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Consumes https://github.com/Baeldung/spring-hypermedia-api
 */
@Slf4j
class BookClientLiveTest {
    private BookClient bookClient;

    @BeforeEach
    void setup() {
        BookControllerFeignClientBuilder feignClientBuilder = new BookControllerFeignClientBuilder();
        bookClient = feignClientBuilder.getBookClient();
    }

    @Test
    void givenBookClient_shouldRunSuccessfully() throws Exception {
        List<Book> books = bookClient.findAll().stream().map(BookResource::getBook).collect(Collectors.toList());
        assertTrue(books.size() > 2);
        log.info("{}", books);
    }

    @Test
    void givenBookClient_shouldFindOneBook() throws Exception {
        Book book = bookClient.findByIsbn("0151072558").getBook();
        assertThat(book.getAuthor(), containsString("Orwell"));
        log.info("{}", book);
    }

    @Test
    void givenBookClient_shouldPostBook() throws Exception {
        String isbn = UUID.randomUUID().toString();
        Book book = new Book(isbn, "Me", "It's me!", null, null);
        bookClient.create(book);

        book = bookClient.findByIsbn(isbn).getBook();
        assertThat(book.getAuthor(), is("Me"));
        log.info("{}", book);
    }
}
