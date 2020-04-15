package com.baeldung.hexagonal.Configuration;

import com.baeldung.hexagonal.repository.BookRepository;
import com.baeldung.hexagonal.repository.InMemoryBookRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class HexagonalArchitectureJavaApplicationConfiguration {

    @Bean
    public BookRepository bookRepository() {
        return new InMemoryBookRepositoryImpl();
    }
}
