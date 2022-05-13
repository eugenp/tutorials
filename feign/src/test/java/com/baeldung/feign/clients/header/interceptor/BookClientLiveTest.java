package com.baeldung.feign.clients.header.interceptor;

import com.baeldung.feign.clients.builder.BookFeignClientBuilder;
import com.baeldung.feign.clients.header.staticheader.BookClient;
import com.baeldung.feign.models.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Consumes https://github.com/Baeldung/spring-hypermedia-api
 */
@Slf4j
public class BookClientLiveTest {
	
    private BookFeignClientBuilder feignClientBuilder;
      	
    private BookClient bookClient;
     		
    @Before
    public void setup() {
        feignClientBuilder = new BookFeignClientBuilder();
        bookClient = feignClientBuilder.createClientWithInterceptor(BookClient.class, "http://localhost:8081/api/books");
    }

    @Test
    public void givenBookClient_shouldFindOneBook() throws Exception {
        Book book = bookClient.findByIsbn("0151072558")
          .getBook();
        log.info("{}", bookClient.toString());
        assertThat(book.getAuthor(), containsString("Orwell"));
        log.info("{}", book);
    }
    
    @Test
    public void givenBookClient2_shouldFindOneBook() throws Exception {
        Book book = bookClient.findByIsbn("0151072558")
          .getBook();
        assertThat(book.getAuthor(), containsString("Orwell"));
        log.info("{}", book);
    }

    @Test
    public void givenBookClient_shouldPostBook() throws Exception {
        String isbn = UUID.randomUUID()
          .toString();
        Book book = new Book(isbn, "Me", "It's me!", null, null);
        bookClient.create(book);

        book = bookClient.findByIsbn(isbn)
          .getBook();
        assertThat(book.getAuthor(), is("Me"));
        log.info("{}", book);
    }
}
