package com.baeldung.fluxmapandondonext;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

class DoOnNextUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoOnNextUnitTest.class);

    @Test
    void givenFluxOfNumbers_whenCountingNumbers_thenReturnCorrectCountAndLogToOutput() {
        AtomicInteger counter = new AtomicInteger(0);

        Flux<Integer> numberFlux = Flux.just(1, 2, 3, 4, 5)
            .doOnNext(number -> {
                LOGGER.info(String.valueOf(number));
                counter.incrementAndGet();
            })
            .onErrorResume(Flux::error);

        StepVerifier.create(numberFlux)
            .expectNextCount(5)
            .verifyComplete();

        assertEquals(5, counter.get());
    }

    @Test
    void givenNumberServiceRange_whenTransformingAndAddingSideEffect_thenReturnTransformedNumbers() {
        Flux<Integer> numbersFlux = Flux.just(10, 11, 12, 13, 14)
            .doOnNext(number -> {
                LOGGER.info("Number: " + number);
            })
            .map(i -> i * 5)
            .doOnNext(number -> {
                LOGGER.info("Transformed Number: " + number);
            })
            .onErrorResume(Flux::error);

        StepVerifier.create(numbersFlux)
            .expectNext(50, 55, 60, 65, 70)
            .verifyComplete();
    }

}