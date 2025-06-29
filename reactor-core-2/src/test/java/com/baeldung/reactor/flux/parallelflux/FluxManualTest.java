package com.baeldung.reactor.flux.parallelflux;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;

@Slf4j
public class FluxManualTest {

    @Test
    public void givenFibonacciIndices_whenComputingWithFlux_thenCorrectResults() {
        Flux<Long> fluxFibonacci = Flux.just(43, 44, 45, 47, 48)
                .publishOn(Schedulers.boundedElastic())
                .map(Fibonacci::fibonacci);

        long startTime = System.nanoTime();
        StepVerifier.create(fluxFibonacci)
                .expectNext(433494437L, 701408733L, 1134903170L, 2971215073L, 4807526976L)
                .verifyComplete();

        long endTime = System.nanoTime();
        Duration duration = Duration.ofNanos(endTime - startTime);

        log.info("Total time taken for Flux pipeline processing Fibonacci indices 43, 44, 45, 47, 48: {} ms",
                duration);
    }

}
