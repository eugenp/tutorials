package org.baeldung.boot;

import org.baeldung.boot.client.BookConsumer;
import org.baeldung.boot.client.BookProducer;
import org.baeldung.boot.domain.BookRepository;
import org.baeldung.boot.domain.BookService;
import org.baeldung.boot.domain.BookServiceImpl;
import org.baeldung.boot.repository.InMemoryBookRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public BookRepository bookRepository() {
        return new InMemoryBookRepositoryImpl();
    }

    @Bean
    public BookService bookService(BookRepository bookRepository) {
        return new BookServiceImpl(bookRepository);
    }

    @Bean
    public BookProducer bookProducer(BookService bookService) {
        return new BookProducer(bookService);
    }

    @Bean
    public BookConsumer bookConsumer(BookService bookService) {
        return new BookConsumer(bookService);
    }
}
