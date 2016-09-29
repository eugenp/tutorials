package com.baeldung.feign.clients;

import com.baeldung.feign.models.Book;
import com.baeldung.feign.models.BookResource;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class BookClientTest {
    private static final Logger log = LoggerFactory.getLogger(BookClientTest.class);

    private BookClient bookClient = Feign.builder()
      .client(new OkHttpClient())
      .encoder(new GsonEncoder())
      .decoder(new GsonDecoder())
      .logger(new Slf4jLogger(BookClient.class))
      .logLevel(feign.Logger.Level.FULL)
      .target(BookClient.class, "http://localhost:8081/api/books");

    @Test
    public void givenBookClient_shouldRunSuccessfully() throws Exception {
        List<Book> books = bookClient.findAll().stream()
          .map(BookResource::getBook)
          .collect(Collectors.toList());
        assertTrue(books.size() > 2);
        log.info("{}", books);
    }

    @Test
    public void givenBookClient_shouldFindOneBook() throws Exception {
        Book book = bookClient.findByIsbn("0151072558").getBook();
        assertThat(book.getAuthor(), containsString("Orwell"));
        log.info("{}", book);
    }

    @Test
    public void givenBookClient_shouldPostBook() throws Exception {
        String isbn = UUID.randomUUID().toString();
        Book book = new Book(isbn, "Me", "It's me!", null, null);
        bookClient.create(book);

        book = bookClient.findByIsbn(isbn).getBook();
        assertThat(book.getAuthor(), is("Me"));
        log.info("{}", book);
    }
}
