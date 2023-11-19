package com.baeldung.h2.exceptions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:app-h2.properties")
public class SpringBootH2Exceptions {

    public static void main(String... args) {
        SpringApplication.run(SpringBootH2Exceptions.class, args);
    }

}
