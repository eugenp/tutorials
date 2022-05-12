package com.baeldung.feign.clients.header.dynamicheader;

import com.baeldung.feign.clients.builder.BookFeignClientBuilder;
import com.baeldung.feign.models.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        bookClient = feignClientBuilder.createClient(BookClient.class, "http://localhost:8081/api/books");
    }

    @Test
    public void givenBookClient_shouldPostBook() throws Exception {
        String isbn = UUID.randomUUID()
               .toString();
    	
        Book book = new Book(isbn, "Me", "It's me!", null, null);
        
        feignClientBuilder = new BookFeignClientBuilder();
       
        Map<String,Object> headerMap = new HashMap<>();
    	
        headerMap.put("metadata_key1", "metadata_value1");
        headerMap.put("metadata_key2", "metadata_value2");   	
    	
        bookClient.create(headerMap, book);
        log.info("{}", book);
    }
}
