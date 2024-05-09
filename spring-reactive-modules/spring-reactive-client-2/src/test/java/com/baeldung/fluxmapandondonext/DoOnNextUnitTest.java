package com.baeldung.fluxmapandondonext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicInteger;

import static com.baeldung.fluxmapandondonext.DoOnNext.number_service;
import static com.baeldung.fluxmapandondonext.DoOnNext.number_service_range;

class DoOnNextUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoOnNextUnitTest.class);

    @Test
    void givenFluxOfNumbers_whenCountingNumbers_thenReturnCorrectCountAndLogToOutput() {
        AtomicInteger counter = new AtomicInteger(0);

        Flux<Integer> numberFlux = number_service().doOnNext(number -> {
                LOGGER.info(String.valueOf(number));
                counter.incrementAndGet();
            })
            .onErrorResume(Flux::error);

        StepVerifier.create(numberFlux)
            .expectNextCount(5)
            .verifyComplete();

        Assertions.assertEquals(counter.get(), 5);
    }

    @Test
    void givenNumberServiceRange_whenTransformingAndAddingSideEffect_thenReturnTransformedNumbers() {
        Flux<Integer> numbersFlux = number_service_range().doOnNext(number -> {
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