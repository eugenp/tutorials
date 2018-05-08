package com.baeldung.reactive.webclient;

import java.util.Collections;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.reactive.model.Stock;

@SpringBootApplication
public class StockClient {

    @Bean
    WebClient webClient() {
        return WebClient.builder()
            .baseUrl("http://localhost:8080/getStockPrice")
            .build();
    }

    @Bean
    CommandLineRunner runner(WebClient webClient) {
        return args -> {
            webClient.get()
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(Stock.class)
                .log()
                .subscribe(System.out::println);
        };
    }

    public static void main(String args[]) throws InterruptedException {
        new SpringApplicationBuilder(StockClient.class).properties(Collections.singletonMap("server.port", "9090"))
            .run(args);

    }

}
