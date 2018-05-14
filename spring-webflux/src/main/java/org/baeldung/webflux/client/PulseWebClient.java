package org.baeldung.webflux.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class PulseWebClient {

    private static final Logger log = LoggerFactory.getLogger(PulseWebClient.class);

    private WebClient webClient;

    @Autowired
    public PulseWebClient(@Value("${server.port}") String serverPort) {
        this.webClient = WebClient.create("http://localhost:" + serverPort);
    }

    public void listenEvents() {
        final Flux<ServerSentEvent> stream = webClient
          .get()
          .uri("/pulsate")
          .retrieve()
          .bodyToFlux(ServerSentEvent.class)
          .filter(x -> x.event() != null);  //filter check not needed for spring-boot 2.0.2.RELEASE

        stream.subscribe(event -> log.info("Received: {}", event), Throwable::printStackTrace);
    }
}
