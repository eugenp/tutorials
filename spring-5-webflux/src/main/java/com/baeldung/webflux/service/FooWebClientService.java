package com.baeldung.webflux.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.webflux.model.Foo;

@Service
public class FooWebClientService {

    private static final Logger logger = LoggerFactory.getLogger(FooWebClientService.class);

    public void initFooWebClient() {
        WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

        webClient.get()
            .uri("/foos")
            .exchange()
            .log()
            .flatMapMany(response -> response.bodyToFlux(Foo.class))
            .subscribe(foo -> {
                logger.info(
                  String.format("data:{id: %d, name: %s}", foo.getId(), foo.getName()));
            });
    }

}