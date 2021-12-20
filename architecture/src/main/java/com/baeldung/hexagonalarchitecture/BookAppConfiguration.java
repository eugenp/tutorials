package com.baeldung.hexagonalarchitecture;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.hexagonalarchitecture.domain.BookService;
import com.baeldung.hexagonalarchitecture.domain.IBookRepository;
import com.baeldung.hexagonalarchitecture.domain.IBookService;
import com.baeldung.hexagonalarchitecture.infrastructure.DatabaseBookRepository;
import com.baeldung.hexagonalarchitecture.infrastructure.JpaBookRepository;

@Configuration
public class BookAppConfiguration {

    @Bean
    IBookRepository bookRepository(JpaBookRepository jpaBookRepository) {
        return new DatabaseBookRepository(jpaBookRepository);
    }

    @Bean
    IBookService bookService(IBookRepository bookRepository) {
        return new BookService(bookRepository);
    }
}