package com.baeldung.examples.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.examples.common.BookService;
import com.baeldung.examples.common.BookServiceImpl;

@Configuration
@ComponentScan("com.baeldung.examples")
public class AppConfig {

    @Bean
    public BookService bookServiceGenerator() {
        return new BookServiceImpl();
    }

}
