package com.baeldung.reactive.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class RealTimeEventClientController {

    private WebClient webClient = WebClient.builder()
        .baseUrl("http://localhost:8080/")
        .build();
    private Logger logger = LoggerFactory.getLogger(RealTimeEventClientController.class);

    @GetMapping(value = "/client/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public String getEvents() {
        webClient.get()
            .uri("events")
            .header(HttpHeaders.ACCEPT, MediaType.TEXT_EVENT_STREAM_VALUE)
            .retrieve()
            .bodyToFlux(String.class)
            .subscribe((e) -> {
                logger.info("Received event :" + e);
            });
        return "Invoked";
    }
}
