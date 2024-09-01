package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class OpenApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenApiApplication.class, args);
    }
}
