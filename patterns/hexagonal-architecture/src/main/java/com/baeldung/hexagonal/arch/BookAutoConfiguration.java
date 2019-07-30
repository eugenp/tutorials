package com.baeldung.hexagonal.arch;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.hexagonal.arch.inside.BookService;
import com.baeldung.hexagonal.arch.inside.Bookstore;
import com.baeldung.hexagonal.arch.outside.BookstoreController;
import com.baeldung.hexagonal.arch.outside.JpaBookstore;

@Configuration
public class BookAutoConfiguration {
    @ConditionalOnMissingBean(name = "bookstoreController")
    @Bean
    public BookstoreController bookstoreController() {
        return new BookstoreController(bookService());
    }

    @ConditionalOnMissingBean(name = "bookService")
    @Bean
    public BookService bookService() {
        return new BookService(bookstore());
    }

    @Bean
    @ConditionalOnMissingBean(name = "bookstore")
    public Bookstore bookstore() {
        return new JpaBookstore();
    }
}
