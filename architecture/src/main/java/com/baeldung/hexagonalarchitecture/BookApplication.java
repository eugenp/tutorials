package com.baeldung.hexagonalarchitecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = { "classpath:hexagonal-architecture.properties" })
public class BookApplication {

    public static void main(final String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }
}
