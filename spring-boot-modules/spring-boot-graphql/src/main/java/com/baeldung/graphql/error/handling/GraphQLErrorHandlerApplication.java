package com.baeldung.graphql.error.handling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GraphQLErrorHandlerApplication {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.default", "error-handling");
        SpringApplication.run(GraphQLErrorHandlerApplication.class, args);
    }
}
