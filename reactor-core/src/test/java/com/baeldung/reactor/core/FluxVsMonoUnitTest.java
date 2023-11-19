package com.baeldung.reactor.core;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxVsMonoUnitTest {

    @Test
    public void givenMonoPublisher_whenSubscribeThenReturnSingleValue() {
        Mono<String> helloMono = Mono.just("Hello");
        StepVerifier.create(helloMono)
          .expectNext("Hello")
          .expectComplete()
          .verify();
    }

    @Test
    public void givenFluxPublisher_whenSubscribeThenReturnMultipleValues() {
        Flux<String> stringFlux = Flux.just("Hello", "Baeldung");
        StepVerifier.create(stringFlux)
          .expectNext("Hello")
          .expectNext("Baeldung")
          .expectComplete()
          .verify();
    }

    @Test
    public void givenFluxPublisher_whenSubscribeThenReturnMultipleValuesWithError() {
        Flux<String> stringFlux = Flux.just("Hello", "Baeldung", "Error")
          .map(str -> {
              if (str.equals("Error"))
                  throw new RuntimeException("Throwing Error");
              return str;
          });
        StepVerifier.create(stringFlux)
          .expectNext("Hello")
          .expectNext("Baeldung")
          .expectError()
          .verify();
    }

}
