package com.baeldung.fluxmapandondonext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import static com.baeldung.fluxmapandondonext.FluxMap.numerical_values;
import static com.baeldung.fluxmapandondonext.FluxMap.numerical_values_2;

class FluxMapUnitTest {

    @Test
    void givenSequenceOfValue_whenMapOperatorTransformEmittedData_thenReturnNewData() {
        Flux<Integer> numbersFlux = numerical_values().map(i -> i * 10);

        StepVerifier.create(numbersFlux)
            .expectNext(500, 510, 520, 530, 540, 550, 560, 570, 580, 590)
            .verifyComplete();
    }

    @Test
    public void transforming_sequence_2() {
        Flux<Integer> numbersFlux = numerical_values_2();

        Flux<String> resultSequence = numbersFlux.map(i -> {
            if (i > 0) {
                return ">";
            } else if (i == 0) {
                return "=";
            } else {
                return "<";
            }
        });

        resultSequence.subscribe(System.out::println);

        StepVerifier.create(resultSequence)
            .expectNext(">", "<", "=", ">", ">")
            .verifyComplete();
    }


}