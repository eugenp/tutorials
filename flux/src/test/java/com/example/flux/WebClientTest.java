package com.example.flux;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

public class WebClientTest {
    Logger LOG = LoggerFactory.getLogger(WebClientTest.class);

    @Test
    public void whenConnectingWebClient_thenReceivingAStream() throws InterruptedException {
        WebClient webClient = WebClient.builder()
          .baseUrl("http://localhost:8080")
          .defaultHeader(HttpHeaders.ACCEPT, "text/event-stream")
          .build();

        Flux<String> timestampsStream = webClient.get().uri("/timestamp").retrieve().bodyToFlux(String.class);
        timestampsStream.subscribe((ts) -> LOG.info("Current server side timestamp is {}", ts));

        Thread.sleep(300_000);
    }
}
