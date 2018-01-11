package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import com.baeldung.beaninjection.controller.BookController;
import com.baeldung.beaninjection.model.Book;

@ComponentScan(basePackages = { "com.baeldung.beaninjection", "com.baeldung.beaninjection.controller", "com.baeldung.beaninjection.model", "com.baeldung.beaninjection.service" })
@Configuration
public class BookConfiguration {

    @Autowired
    BookController bookController;

    @Bean
    @Scope("prototype")
    public Book book() {
        return new Book("Harry Potter And The Deathly Hollows", "J. K. Rowling");
    }

}
