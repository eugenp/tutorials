package com.baeldung.reactive.servicesenteventconsumer;

import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/sse-consumer")
public class EventConsumerController {

    private static Logger logger = LoggerFactory.getLogger(EventConsumerController.class);

    @GetMapping("/launch-sse-client")
    public String launchEventConsumerWebClient() {
        consumeServerSentEvent();
        return "LAUNCHED EVENT CLIENT!!! Check the logs...";
    }

    @GetMapping("/launch-content-client")
    public String launchContentConsumerWebClient() {
        consumeSSEContent();
        return "LAUNCHED SSE CONTENT CLIENT!!! Check the logs...";
    }

    @Async
    public void consumeSSEContent() {
        WebClient client = WebClient.create("http://localhost:8080/sse");

        Flux<String> stringStream = client.get()
            .uri("/stream")
            .retrieve()
            .bodyToFlux(String.class);

        stringStream.subscribe(content -> logger.info("{} - Received content: {} ", LocalTime.now(), content),
            error -> logger.error("Error retrieving content: {}", error),
            () -> logger.info("Completed!!!"));
    }

    @Async
    public void consumeServerSentEvent() {
        WebClient client = WebClient.create("http://localhost:8080/sse");

        ParameterizedTypeReference<ServerSentEvent<String>> type = new ParameterizedTypeReference<ServerSentEvent<String>>() {
        };

        Flux<ServerSentEvent<String>> eventStream = client.get()
            .uri("/stream")
            .retrieve()
            .bodyToFlux(type);

        eventStream.subscribe(content -> logger.info("{} - Received SSE: name[{}], id [{}], content[{}] ", LocalTime.now(), content.event(), content.id(), content.data()),
            error -> logger.error("Error receiving SSE: {}", error),
            () -> logger.info("Completed!!!"));
    }
}
