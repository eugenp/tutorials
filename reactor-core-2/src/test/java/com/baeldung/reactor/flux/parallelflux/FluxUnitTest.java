package com.baeldung.reactor.flux.parallelflux;

import com.baeldung.reactor.fluxvsparallelflux.Fibonacci;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class FluxUnitTest {

    @Test
    public void givenFibonacciIndices_whenComputingWithFlux_thenCorrectResults() {
        AtomicLong timeTakenForCompletion = new AtomicLong();

        Flux<Long> fluxFibonacci = Flux.just(43, 44, 45, 47, 48)
                .doOnSubscribe(subscription -> timeTakenForCompletion.set(-1 * System.nanoTime()))
                .publishOn(Schedulers.boundedElastic())
                .map(Fibonacci::fibonacci)
                .doFinally(consumer -> timeTakenForCompletion.addAndGet(System.nanoTime()));

        StepVerifier.create(fluxFibonacci)
                .expectNext(433494437L, 701408733L, 1134903170L, 2971215073L, 4807526976L)
                .verifyComplete();


        log.info("Total time taken for Flux pipeline processing Fibonacci indices 43, 44, 45, 47, 48: {} ms",
                Duration.ofNanos(timeTakenForCompletion.get()).toMillis());
    }

}
