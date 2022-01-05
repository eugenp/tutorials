package com.demo.configurations;

import com.demo.impls.BookServiceImpl;
import com.demo.repositories.BookRepository;
import com.demo.services.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    BookService bookService(BookRepository bookRepository) {
        return new BookServiceImpl(bookRepository);
    }
}