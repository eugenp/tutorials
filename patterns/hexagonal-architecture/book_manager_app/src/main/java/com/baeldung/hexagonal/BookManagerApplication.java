package com.baeldung.hexagonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class BookManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookManagerApplication.class, args);
    }
}
