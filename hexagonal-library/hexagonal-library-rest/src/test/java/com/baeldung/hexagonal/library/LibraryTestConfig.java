package com.baeldung.hexagonal.library;

import com.baeldung.hexagonal.library.domain.Book;
import com.baeldung.hexagonal.library.repository.BookRepository;
import com.baeldung.hexagonal.library.repository.InMemBooksRepository;
import com.baeldung.hexagonal.library.service.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Collections.singletonList;

@Configuration
public class LibraryTestConfig {

    @Bean
    public BookRepository bookRepository() {
        Map<UUID, Book> bookMap = new HashMap<>();

        Book b1 = new Book(UUID.randomUUID(), "The Gambler", singletonList("F. Dostoevsky"));

        bookMap.put(b1.getId(), b1);
        return new InMemBooksRepository(bookMap);
    }

    @Bean
    public BookService bookService(BookRepository bookRepository) {
        return new BookService(bookRepository);
    }
}
