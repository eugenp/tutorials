package com.baeldung.beaninjectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibraryConstructorConfiguration {

    @Bean
    public LibraryConstructorInjection library() {
        LibraryConstructorInjection libraryConstructorInjection = new LibraryConstructorInjection(book());
        return libraryConstructorInjection;
    }

    @Bean
    public Book book() {
        Book book = new Book();
        book.setName("Data Structures and Algorithms");
        return book;
    }
}
