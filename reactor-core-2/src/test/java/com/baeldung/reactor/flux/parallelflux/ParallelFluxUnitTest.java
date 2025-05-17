package com.baeldung.reactor.flux.parallelflux;

import com.baeldung.reactor.fluxvsparallelflux.Fibonacci;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class ParallelFluxUnitTest {

    @Test
    public void givenFibonacciIndices_whenComputingWithParallelFlux_thenCorrectResults() {
        AtomicLong timeTakenForCompletion = new AtomicLong();

        ParallelFlux<Long> parallelFluxFibonacci = Flux.just(43, 44, 45, 47, 48)
                .parallel(3)
                .doOnSubscribe(subscription -> timeTakenForCompletion.set(-1 * System.nanoTime()))
                .runOn(Schedulers.parallel())
                .map(Fibonacci::fibonacci);

        Flux<Long> sequencialParallelFlux = parallelFluxFibonacci.sequential()
                .doFinally(consumer -> timeTakenForCompletion.addAndGet(System.nanoTime()));

        StepVerifier.create(sequencialParallelFlux)
                .expectNextMatches(n -> List.of(433494437L, 701408733L, 1134903170L, 2971215073L, 4807526976L).contains(n))
                .expectNextMatches(n -> List.of(433494437L, 701408733L, 1134903170L, 2971215073L, 4807526976L).contains(n))
                .expectNextMatches(n -> List.of(433494437L, 701408733L, 1134903170L, 2971215073L, 4807526976L).contains(n))
                .expectNextMatches(n -> List.of(433494437L, 701408733L, 1134903170L, 2971215073L, 4807526976L).contains(n))
                .expectNextMatches(n -> List.of(433494437L, 701408733L, 1134903170L, 2971215073L, 4807526976L).contains(n))
                .verifyComplete();


        log.info("Total time taken for Parallel Flux pipeline processing Fibonacci indices 43, 44, 45, 47, 48: {} ms", Duration.ofNanos(timeTakenForCompletion.get()).toMillis());
    }

    @Test
    public void givenListOfIds_whenComputingWithParallelFlux_OrderChanges() {
        ParallelFlux<String> parallelFlux = Flux.just("id1", "id2", "id3")
                .parallel(2)
                .runOn(Schedulers.parallel())
                .map(String::toUpperCase);

        StepVerifier.create(parallelFlux.sequential())
                .expectNextMatches(id -> List.of("ID1", "ID2", "ID3").contains(id))
                .expectNextMatches(id -> List.of("ID1", "ID2", "ID3").contains(id))
                .expectNextMatches(id -> List.of("ID1", "ID2", "ID3").contains(id))
                .verifyComplete();
    }
}
