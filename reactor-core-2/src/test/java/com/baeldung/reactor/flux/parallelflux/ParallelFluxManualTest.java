package com.baeldung.reactor.flux.parallelflux;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.Main;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class ParallelFluxManualTest {

    @Test
    public void givenFibonacciIndices_whenComputingWithParallelFlux_thenRunBenchMarks() throws IOException {
        Main.main(new String[] {
                "com.baeldung.reactor.flux.parallelflux.FibonacciFluxParallelFluxBenchmark.benchMarkParallelFluxSequential",
                "-i", "3",
                "-wi", "2",
                "-f", "1"
        });
    }

    @Test
    public void givenFibonacciIndices_whenComputingWithParallelFlux_thenCorrectResults() {
        ParallelFlux<Long> parallelFluxFibonacci = Flux.just(43, 44, 45, 47, 48)
                .parallel(3)
                .runOn(Schedulers.parallel())
                .map(Fibonacci::fibonacci);

        Flux<Long> sequencialParallelFlux = parallelFluxFibonacci.sequential();

        Set<Long> expectedSet = new HashSet<>(Set.of(433494437L, 701408733L, 1134903170L, 2971215073L, 4807526976L));

        StepVerifier.create(sequencialParallelFlux)
                .expectNextMatches(expectedSet::remove)
                .expectNextMatches(expectedSet::remove)
                .expectNextMatches(expectedSet::remove)
                .expectNextMatches(expectedSet::remove)
                .expectNextMatches(expectedSet::remove)
                .verifyComplete();

    }

    @RepeatedTest(5)
    public void givenListOfIds_whenComputingWithParallelFlux_thenOrderChanges() {
        ParallelFlux<String> parallelFlux = Flux.just("id1", "id2", "id3")
                .parallel(2)
                .runOn(Schedulers.parallel())
                .map(String::toUpperCase);

        List<String> emitted = new CopyOnWriteArrayList<>();

        StepVerifier.create(parallelFlux.sequential().doOnNext(emitted::add))
                .expectNextCount(3)
                .verifyComplete();

        log.info("ParallelFlux emitted order: {}", emitted);
    }
}
