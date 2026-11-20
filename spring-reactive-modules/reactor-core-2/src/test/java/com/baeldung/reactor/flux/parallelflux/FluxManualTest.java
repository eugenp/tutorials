package com.baeldung.reactor.flux.parallelflux;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.Main;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.io.IOException;

@Slf4j
public class FluxManualTest {

    @Test
    public void givenFibonacciIndices_whenComputingWithFlux_thenRunBenchMarks() throws IOException {
        Main.main(new String[] {
                "com.baeldung.reactor.flux.parallelflux.FibonacciFluxParallelFluxBenchmark.benchMarkFluxSequential",
                "-i", "3",
                "-wi", "2",
                "-f", "1"
        });
    }

    @Test
    public void givenFibonacciIndices_whenComputingWithFlux_thenCorrectResults() {
        Flux<Long> fluxFibonacci = Flux.just(43, 44, 45, 47, 48)
                .publishOn(Schedulers.boundedElastic())
                .map(Fibonacci::fibonacci);

        StepVerifier.create(fluxFibonacci)
                .expectNext(433494437L, 701408733L, 1134903170L, 2971215073L, 4807526976L)
                .verifyComplete();

    }

}
