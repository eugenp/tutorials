package com.baeldung.hexagonal.architecture;

import com.baeldung.hexagonal.architecture.application.boundary.BookRepository;
import com.baeldung.hexagonal.architecture.infrastructure.driven.dataside.control.DatabaseBookRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public BookRepository bookRepository() {
        return new DatabaseBookRepository();
    }
}
