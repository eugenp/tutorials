package com.baeldung.springwebfluxstreaming;

import com.baeldung.reactive.Spring5ReactiveApplication;
import java.io.IOException;
import java.time.Duration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Spring5ReactiveApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringWebFluxStreamingUnitTest {
    @Autowired
    private WebTestClient webClient;

    @Before
    public void setup() {
        webClient =  webClient.mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();
    }

    @Test
    public void whenRetrievingEvents_thenExpectCorrectStatusCode() throws IOException{
        FluxExchangeResult<Integer> result = this.webClient
                .get()
                .uri("/events")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class);

        Flux<Integer> eventFlux = result.getResponseBody();

        StepVerifier.create(eventFlux)
                .expectNextCount(5)
                .thenCancel()
                .verify();
    }
}

