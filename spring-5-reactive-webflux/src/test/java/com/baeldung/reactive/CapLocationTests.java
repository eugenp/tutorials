package com.baeldung.reactive;

import java.time.Duration;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.reactive.controller.CabController;
import com.baeldung.reactive.model.CabLocation;
import com.baeldung.reactive.service.CabService;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
/**
 * Unit test for testing Cab Locations
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CabController.class, CabService.class })
@WebFluxTest(controllers = { CabController.class })
public class CapLocationTests {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void whenGetAPIConsumed_thenShouldPrintTheLocationAtOneSecInterval() {

        // The id of the booked cab
        String cabId = UUID.randomUUID().toString();

        // URI of the API
        String uri = "http://localhost:8080/cab/location/" + cabId;

        // @formatter:off
        Flux<CabLocation> resultFlux = webClient.get()
                                                .uri(uri).accept(MediaType.APPLICATION_STREAM_JSON)
                                                .exchange()
                                                .returnResult(CabLocation.class)
                                                .getResponseBody();
        
        
        StepVerifier.create(resultFlux)        
                    .expectSubscription()
                    .thenAwait(Duration.ofSeconds(1))
                    .assertNext(location -> Assertions.assertThat(location)
                                                      .hasFieldOrPropertyWithValue("cabId", cabId))
                    .thenAwait(Duration.ofSeconds(1))
                    .assertNext(location -> Assertions.assertThat(location)
                            .hasFieldOrPropertyWithValue("cabId", cabId))
                    .thenAwait(Duration.ofSeconds(1))
                    .assertNext(location -> Assertions.assertThat(location)
                            .hasFieldOrPropertyWithValue("cabId", cabId))
                    .thenCancel()
                    .verify();
        
        // @formatter:on        
    }
}
