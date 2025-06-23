package com.baeldung.reactor.flux.parallelflux;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class ParallelFluxUnitTest {

    @Test
    public void givenFibonacciIndices_whenComputingWithParallelFlux_thenCorrectResults() {

        ParallelFlux<Long> parallelFluxFibonacci = Flux.just(43, 44, 45, 47, 48)
                .parallel(3)
                .runOn(Schedulers.parallel())
                .map(Fibonacci::fibonacci);

        Flux<Long> sequencialParallelFlux = parallelFluxFibonacci.sequential();

        Set<Long> expectedSet = new HashSet<>(Set.of(433494437L, 701408733L, 1134903170L, 2971215073L, 4807526976L));

        long startTime = System.nanoTime();

        StepVerifier.create(sequencialParallelFlux)
                .expectNextMatches(expectedSet::remove)
                .expectNextMatches(expectedSet::remove)
                .expectNextMatches(expectedSet::remove)
                .expectNextMatches(expectedSet::remove)
                .expectNextMatches(expectedSet::remove)
                .verifyComplete();

        long endTime = System.nanoTime();
        Duration duration = Duration.ofNanos(endTime - startTime);

        log.info("Total time taken for Parallel Flux pipeline processing Fibonacci indices 43, 44, 45, 47, 48: {} ms", duration);
    }

    @Test
    public void givenListOfIds_whenComputingWithParallelFlux_OrderChanges() {
        ParallelFlux<String> parallelFlux = Flux.just("id1", "id2", "id3")
                .parallel(2)
                .runOn(Schedulers.parallel())
                .map(String::toUpperCase);

        List<String> expectedIds = new ArrayList<>(List.of("ID1", "ID2", "ID3"));

        StepVerifier.create(parallelFlux.sequential())
                .expectNextMatches(expectedIds::remove)
                .expectNextMatches(expectedIds::remove)
                .expectNextMatches(expectedIds::remove)
                .verifyComplete();
    }
}
