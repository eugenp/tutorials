package com.baeldung.reactive.client;

import com.baeldung.reactive.model.Ping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class PingClient {
    private static final Logger LOG = LoggerFactory.getLogger(PingClient.class);

    WebClient client = WebClient.create("http://localhost:8080");

    public void consumes() {
        Flux<Ping> pingFlux = client.get()
                .uri("/ping")
                .retrieve()
                .bodyToFlux(Ping.class);

        pingFlux.subscribe((ping -> LOG.info("Ping {} received", ping)));
    }
}
