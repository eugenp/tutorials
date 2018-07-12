package com.baeldung.reactive;

import com.baeldung.reactive.client.WebFluxClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class Spring5ReactiveClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring5ReactiveClientApplication.class, args);
    }

    @Bean
    public CommandLineRunner printBookCommandLineApplication() {
        return args ->  {
            WebFluxClient webFluxClient = new WebFluxClient();
            Flux<String> bookNames = webFluxClient.getBookNames(WebClient.create("http://localhost:8080/books/names"));
            webFluxClient.logBookNames(bookNames);
        };
    }

}
