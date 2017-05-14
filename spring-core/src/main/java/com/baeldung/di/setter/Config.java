package com.baeldung.di.setter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.di.setter.domain.Author;
import com.baeldung.di.setter.domain.Book;

@Configuration
@ComponentScan("com.baeldung.di.setter.domain")
public class Config {

    @Bean
    protected String authorNameBean() {
        return "AuthorName";
    }

    @Bean
    protected Author authorBean() {
        Author author = new Author();
        author.setName(authorNameBean());
        return author;
    }

    @Bean("book")
    public Book bookBean() {
        Book book = new Book();
        book.setAuthor(authorBean());
        return book;
    }

}
