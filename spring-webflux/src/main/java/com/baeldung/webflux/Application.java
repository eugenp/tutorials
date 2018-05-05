package com.baeldung.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        WebClient.create("http://localhost:8080")
            .get()
            .uri("/text")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .exchange();

    }

}
