package com.baeldung.reactive.eventstreaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class NewsSpringApplication {

    public static void main(String[] args) {

        SpringApplication.run(NewsSpringApplication.class, args);

        NewsWebClient newsWebClient = new NewsWebClient();
        newsWebClient.consume();
    }

}
