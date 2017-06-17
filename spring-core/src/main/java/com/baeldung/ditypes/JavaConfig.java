package com.baeldung.ditypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.ditypes.domain.Book;
import com.baeldung.ditypes.domain.Category;

@Configuration
public class JavaConfig {

    @Bean
    public Category category() {
        return new Category("Classic");
    }

    @Bean(name = "bookJavaConstructor")
    public Book bookJavaConstructorInjection() {
        return new Book("Pride and Prejudice", category());
    }

    @Bean(name = "bookJavaSetter")
    public Book bookJavaSetterInjection() {
        Book book = new Book();
        book.setName("Pride and Prejudice");
        book.setCategory(category());

        return book;
    }
}