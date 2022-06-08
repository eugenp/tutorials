package com.baeldung.feign.clients.header.dynamicheader;

import com.baeldung.feign.clients.builder.BookFeignClientBuilder;
import com.baeldung.feign.models.Book;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Consumes https://github.com/Baeldung/spring-hypermedia-api
 */
public class BookClientLiveTest {
	
    private BookClient bookClient;

    @Before
    public void setup() {
        bookClient = BookFeignClientBuilder.createClient(BookClient.class, "http://localhost:8081/api/books");
    }

    @Test
    public void givenBookClient_shouldPostBook() throws Exception {
        String isbn = UUID.randomUUID()
               .toString();
    	
        Book book = new Book(isbn, "Me", "It's me!", null, null);
        
        Map<String,Object> headerMap = new HashMap<>();
    	
        headerMap.put("metadata-key1", "metadata-value1");
        headerMap.put("metadata-key2", "metadata-value2");   	
    	
        bookClient.create(headerMap, book);
    }
}
