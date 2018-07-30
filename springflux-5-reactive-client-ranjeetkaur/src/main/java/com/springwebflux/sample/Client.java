package com.springwebflux.sample;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

/**
 * @author ranjeetkaur
 *
 */
@SpringBootApplication(scanBasePackages = "com.springwebflux.*")
@EnableAsync
public class Client {

    public static void main(String[] args) throws InterruptedException {

        WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8090")
            .build();

        Flux<Integer> flux = webClient.get()
            .uri("/v1/dice")
            .retrieve()
            .bodyToFlux(Integer.class);
        
        flux.log();

        Thread.sleep(10000);
    }
}