package com.baeldung.webflux.client;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class SpringWebfluxClient {

    @Bean
    InitializingBean logEvents() {
        return () -> WebClient.create("http://localhost:9001")
            .get()
            .uri("/time")
            .retrieve()
            .bodyToFlux(Long.class)
            .subscribe(value -> LoggerFactory.getLogger(SpringWebfluxClient.class)
                .info("Received {}", value));
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringWebfluxClient.class).properties("server.port=9002")
            .run(args);
    }
}
