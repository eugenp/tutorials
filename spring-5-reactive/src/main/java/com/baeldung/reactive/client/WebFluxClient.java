package com.baeldung.reactive.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.boot.SpringApplication.run;


@SpringBootApplication
public class WebFluxClient {

    public static void main(String[] args) {
        run(WebFluxClient.class, args);
    }

    @Bean
    public CommandLineRunner printBookNames() {
        return args -> {
            WebClient client = WebClient.create("http://localhost:8080");

            client.get().uri("/books/names").exchange()
              .flatMapMany(bookName -> bookName.bodyToFlux(String.class))
              .subscribe(System.out::println);
        };
    }
}