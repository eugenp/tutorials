package com.baeldung.java.hexagonal.config;

import com.baeldung.java.hexagonal.ports.inbound.BookService;
import com.baeldung.java.hexagonal.service.InMemoryBookRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookConfiguration {

    @Bean
    public BookService bookService(InMemoryBookRepository inMemoryBookRepository){
        return new BookService(inMemoryBookRepository);
    }
}
