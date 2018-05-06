package com.baeldung.reactive.webflux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class BookClient {

    private static final Logger logger = LoggerFactory.getLogger(BookClient.class);

    @GetMapping("/client/books")
    public void listAllBooks() {

        WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

        webClient.get()
            .uri("/books")
            .exchange()
            .log()
            .flatMapMany(clientResponse -> clientResponse.bodyToFlux(Book.class))
            .subscribe(book -> {
                logger.info("Received {}", book.toString());
            });

    }

}
