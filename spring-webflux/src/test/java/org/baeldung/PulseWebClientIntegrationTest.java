package org.baeldung;

import org.baeldung.webflux.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PulseWebClientIntegrationTest {
    private WebClient webClient;

    @LocalServerPort private String port;

    @Test
    public void testListenEvents() {
        this.webClient = WebClient.create("http://localhost:" + this.port);

        Flux<ServerSentEvent> serverSentEventFlux = this.webClient
          .get()
          .uri("/pulsate")
          .retrieve()
          .bodyToFlux(ServerSentEvent.class)
          .filter(x -> x.event() != null);

        StepVerifier
          .create(serverSentEventFlux)
          .expectNextMatches(x -> x
            .event()
            .equals("heartbeat") && x
            .id()
            .equals("0"))
          .expectNextMatches(x -> x
            .event()
            .equals("heartbeat") && x
            .id()
            .equals("1"))
          .thenCancel()
          .verify(Duration.ofSeconds(3L));
    }
}
