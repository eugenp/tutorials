package com.baeldung.reactive.realtimestream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class InfiniteStreamIntegrationTest {

    WebClient webClient = WebClient.create();

    @Test
    public void shouldStreamEvents() {
        LocalDateTime now = LocalDateTime.now();

        Flux<StreamEvent> flux = webClient.get()
                .uri("http://localhost:8080/events")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(StreamEvent.class)
                .doOnNext((event) -> System.out.println("Got event: " + event))
                .take(2);

        StepVerifier.create(flux)
                .thenAwait(Duration.ofSeconds(1000))
                .expectNextMatches(event -> event.getId().equals("0") && event.getDate().isAfter(now))
                .thenAwait(Duration.ofSeconds(1000))
                .expectNextMatches(event -> event.getId().equals("1") && event.getDate().isAfter(now))
                .expectComplete()
                .verify();
    }

}