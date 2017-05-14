package com.baeldung.di.cons;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.di.cons.domain.Author;
import com.baeldung.di.cons.domain.Book;

@Configuration
@ComponentScan("com.baelding.di.cons.domain")
public class Config {

    @Bean
    protected Author authorBean() {
        return new Author("AuthorName");
    }

    @Bean
    public Book bookBean() {
        return new Book(authorBean());
    }

}
