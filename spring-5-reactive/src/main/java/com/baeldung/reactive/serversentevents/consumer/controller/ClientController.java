package com.baeldung.reactive.serversentevents.consumer.controller;

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sse-consumer")
public class ClientController {

    private static Logger logger = LoggerFactory.getLogger(ClientController.class);
    private WebClient client = WebClient.create("http://localhost:8081/sse-server");

    @GetMapping("/launch-sse-from-sse-client")
    public String launchSSEFromSSEWebClient() {
        consumeSSEFromSSE();
        return "LAUNCHED EVENT CLIENT!!! Check the logs...";
    }

    @GetMapping("/launch-flux-from-sse-client")
    public String launchcFluxFromSSEWebClient() {
        consumeFluxFromSSE();
        return "LAUNCHED SSE CONTENT CLIENT!!! Check the logs...";
    }

    @GetMapping("/launch-flux-from-flux-client")
    public String launchFluxFromFluxWebClient() {
        consumeFluxFromFlux();
        return "LAUNCHED EVENT CLIENT!!! Check the logs...";
    }

    @GetMapping("/launch-sse-from-flux-client")
    public String launchSSEFromFluxWebClient() {
        consumeSSEFromFlux();
        return "LAUNCHED SSE CONTENT CLIENT!!! Check the logs...";
    }

    @GetMapping("/launch-mono-from-mono-client")
    public String launchMonoFromMonoWebClient() {
        consumeMonoFromMono();
        return "LAUNCHED EVENT CLIENT!!! Check the logs...";
    }

    @Async
    public void consumeFluxFromSSE() {
        Flux<String> stringStream = client.get()
            .uri("/stream-sse")
            .retrieve()
            .bodyToFlux(String.class);

        stringStream.subscribe(content -> logger.info("Current time: {} - Received content: {} ", LocalTime.now(), content), error -> logger.error("Error retrieving content: {}", error), () -> logger.info("Completed!!!"));
    }

    @Async
    public void consumeSSEFromSSE() {
        ParameterizedTypeReference<ServerSentEvent<String>> type = new ParameterizedTypeReference<ServerSentEvent<String>>() {
        };

        Flux<ServerSentEvent<String>> eventStream = client.get()
            .uri("/stream-sse")
            .retrieve()
            .bodyToFlux(type);

        eventStream.subscribe(content -> logger.info("Current time: {} - Received SSE: name[{}], id [{}], content[{}] ", LocalTime.now(), content.event(), content.id(), content.data()), error -> logger.error("Error receiving SSE: {}", error),
            () -> logger.info("Completed!!!"));
    }

    @Async
    public void consumeFluxFromFlux() {
        Flux<String> stringStream = client.get()
            .uri("/stream-flux")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(String.class);

        stringStream.subscribe(content -> logger.info("Current time: {} - Received content: {} ", LocalTime.now(), content), error -> logger.error("Error retrieving content: {}", error), () -> logger.info("Completed!!!"));
    }

    @Async
    public void consumeSSEFromFlux() {
        ParameterizedTypeReference<ServerSentEvent<String>> type = new ParameterizedTypeReference<ServerSentEvent<String>>() {
        };

        Flux<ServerSentEvent<String>> eventStream = client.get()
            .uri("/stream-flux")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(type);

        eventStream.subscribe(content -> logger.info("Current time: {} - Received SSE: name[{}], id [{}], content[{}] ", LocalTime.now(), content.event(), content.id(), content.data()), error -> logger.error("Error receiving SSE: {}", error),
            () -> logger.info("Completed!!!"));
    }

    @Async
    public void consumeMonoFromMono() {
        Mono<String> stringStream = client.get()
            .uri("/stream-mono")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToMono(String.class);

        stringStream.subscribe(content -> logger.info("Current time: {} - Received content: {} ", LocalTime.now(), content), error -> logger.error("Error retrieving content: {}", error), () -> logger.info("Completed!!!"));
    }
}
