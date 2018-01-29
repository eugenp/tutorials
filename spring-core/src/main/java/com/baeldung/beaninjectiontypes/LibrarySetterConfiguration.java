package com.baeldung.beaninjectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibrarySetterConfiguration {

    @Bean
    public LibrarySetterInjection library() {
        LibrarySetterInjection librarySetterInjection = new LibrarySetterInjection();
        librarySetterInjection.setBook(book());
        return librarySetterInjection;
    }

    @Bean
    public Book book() {
        Book book = new Book();
        book.setName("Data Structures and Algorithms");
        return book;
    }
}
