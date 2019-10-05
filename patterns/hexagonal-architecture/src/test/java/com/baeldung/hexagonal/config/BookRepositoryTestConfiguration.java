package com.baeldung.hexagonal.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.baeldung.hexagonal.repository.BookRepository;

@Profile("test-book-repo")
@Configuration
public class BookRepositoryTestConfiguration {

    @Bean
    @Primary
    public BookRepository nameServiceTest() {
        return Mockito.mock(BookRepository.class);
    }
}