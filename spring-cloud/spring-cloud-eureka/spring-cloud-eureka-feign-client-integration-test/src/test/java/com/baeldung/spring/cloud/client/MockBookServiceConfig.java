package com.baeldung.spring.cloud.client;

import com.baeldung.spring.cloud.model.Book;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Configuration
@RestController
@ActiveProfiles("eureka-test")
public class MockBookServiceConfig {

    @RequestMapping("/books")
    public List<Book> getBooks() {
        return Collections.singletonList(new Book("Hitchhiker's guide to the galaxy", "Douglas Adams"));
    }

}
