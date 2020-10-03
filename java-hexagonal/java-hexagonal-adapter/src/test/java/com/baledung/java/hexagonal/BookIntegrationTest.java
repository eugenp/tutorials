package com.baledung.java.hexagonal;


import com.baeldung.java.hexagonal.Application;
import com.baeldung.java.hexagonal.model.BookCreateRequest;
import com.baeldung.java.hexagonal.model.BookCreateResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static junit.framework.TestCase.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class BookIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void whenCreateBook_thenBookCreated() {
        BookCreateRequest bookCreateRequest = new BookCreateRequest()
                .setName("Test");
        BookCreateResponse bookCreateResponse = testRestTemplate.postForObject("/books", bookCreateRequest, BookCreateResponse.class);
        assertNotNull(bookCreateResponse);
        assertNotNull(bookCreateResponse.getId());
    }
}
