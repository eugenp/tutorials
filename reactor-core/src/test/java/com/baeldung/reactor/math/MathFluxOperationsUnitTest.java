package com.baeldung.math;

import org.junit.Test;

import com.baeldung.reactor.math.MathFluxOperations;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MathFluxOperationsUnitTest {

    @Test
    public void givenFluxOfNumbers_whenCalculatingSum_thenExpectCorrectResult() {
        Flux<Integer> numbers = Flux.just(1, 2, 3, 4, 5);
        MathFluxOperations mathFluxOperations = new MathFluxOperations();
        Mono<Integer> sumMono = mathFluxOperations.calculateSum(numbers);
        StepVerifier.create(sumMono)
            .expectNext(15)
            .verifyComplete();
    }

    @Test
    public void givenFluxOfNumbers_whenCalculatingAverage_thenExpectCorrectResult() {
        Flux<Integer> numbers = Flux.just(1, 2, 3, 4, 5);
        MathFluxOperations mathFluxOperations = new MathFluxOperations();
        Mono<Double> averageMono = mathFluxOperations.calculateAverage(numbers);
        StepVerifier.create(averageMono)
            .expectNext(3.0)
            .verifyComplete();
    }

    @Test
    public void givenFluxOfNumbers_whenFindingMinElement_thenExpectCorrectResult() {
        Flux<Integer> numbers = Flux.just(3, 1, 5, 2, 4);
        MathFluxOperations mathFluxOperations = new MathFluxOperations();
        Mono<Integer> minMono = mathFluxOperations.findMinElement(numbers);
        StepVerifier.create(minMono)
            .expectNext(1)
            .verifyComplete();
    }

    @Test
    public void givenFluxOfNumbers_whenFindingMaxElement_thenExpectCorrectResult() {
        Flux<Integer> numbers = Flux.just(3, 1, 5, 2, 4);
        MathFluxOperations mathFluxOperations = new MathFluxOperations();
        Mono<Integer> maxMono = mathFluxOperations.findMaxElement(numbers);
        StepVerifier.create(maxMono)
            .expectNext(5)
            .verifyComplete();
    }

}
