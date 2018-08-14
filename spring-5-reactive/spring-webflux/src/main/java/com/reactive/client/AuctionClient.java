package com.reactive.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.reactive.client.AuctionClient;

@SpringBootApplication(scanBasePackages = { "com.reactive" })
public class AuctionClient {

    Logger logger = LoggerFactory.getLogger(AuctionClient.class);

    @Bean
    WebClient getWebClient() {
        return WebClient.create("http://localhost:8081");
    }

    @Bean
    CommandLineRunner demo(WebClient client) {
        return args -> {
            client.get()
                .uri("/bids")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(Integer.class)
                .map(s -> String.valueOf(s))
                .subscribe(msg -> {
                    logger.info(msg);
                });
        };
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(AuctionClient.class).properties(java.util.Collections.singletonMap("server.port", "8081"))
            .run(args);
    }

}
