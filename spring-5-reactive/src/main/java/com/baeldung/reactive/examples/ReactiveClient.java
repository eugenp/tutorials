package com.baeldung.reactive.examples;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ReactiveClient implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveClient.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        WebClient.create("http://localhost:8080")
            .get()
            .uri("/example/time")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(String.class)
            .subscribe(System.out::println);
    }

}
