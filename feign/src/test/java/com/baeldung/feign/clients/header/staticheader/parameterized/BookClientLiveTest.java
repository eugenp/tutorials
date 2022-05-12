package com.baeldung.feign.clients.header.staticheader.parameterized;

import com.baeldung.feign.clients.builder.BookFeignClientBuilder;
import com.baeldung.feign.models.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * Consumes https://github.com/Baeldung/spring-hypermedia-api
 */
@Slf4j
public class BookClientLiveTest {
	
	private BookFeignClientBuilder feignClientBuilder;
	
    private BookClient bookClient;

    private String requester = "test";
    		
    @Before
    public void setup() {
    	feignClientBuilder = new BookFeignClientBuilder();
        bookClient = feignClientBuilder.createClient(BookClient.class, "http://localhost:8081/api/books");
    }

    @Test
    public void givenBookClient_shouldFindOneBook() throws Exception {
        Book book = bookClient.findByIsbn(requester, "0151072558")
          .getBook();
        assertThat(book.getAuthor(), containsString("Orwell"));
        log.info("{}", book);
    }
}
