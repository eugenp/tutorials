package com.baeldung.fluxmapandondonext;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static com.baeldung.fluxmapandondonext.FluxMap.numerical_values;

class FluxMapUnitTest {

    @Test
    void givenNumericalValues_whenTransformedByMap_thenReturnTransformedData() {
        Flux<Integer> numbersFlux = numerical_values().map(i -> i * 10)
            .onErrorResume(Flux::error);

        StepVerifier.create(numbersFlux)
            .expectNext(500, 510, 520, 530, 540, 550, 560, 570, 580, 590)
            .verifyComplete();
    }

}