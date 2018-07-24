package com.baeldung.reactive.webflux.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Date;

@SpringBootApplication
public class ReactiveClient {

    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:8080");
    }

    @Bean
    CommandLineRunner demo(WebClient client) {
        String stockName = "Flipkart";
        return (x) -> {
            client.get()
                    .uri("/api/stock/currentPrice?stockName="+stockName)
                    .accept(MediaType.TEXT_EVENT_STREAM)
                    .exchange()
                    .flatMapMany(response -> response.bodyToFlux(Double.class))
                    .subscribe((price) -> {
                        System.out.println("CurrentTime :" + new Date()
                                + " , StockName:"+stockName+", Price=" + price);
                    });
        };
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ReactiveClient.class)
                .properties(Collections.singletonMap("server.port", "8081"))
                .run(args);
    }
}
