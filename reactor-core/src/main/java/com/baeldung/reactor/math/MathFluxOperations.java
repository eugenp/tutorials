package com.baeldung.reactor.math;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.math.MathFlux;

public class MathFluxOperations {
    public Mono<Integer> calculateSum(Flux<Integer> numbers) {
        return MathFlux.sumInt(numbers);
    }

    public Mono<Double> calculateAverage(Flux<Integer> numbers) {
        return MathFlux.averageDouble(numbers);
    }

    public Mono<Integer> findMinElement(Flux<Integer> numbers) {
        return MathFlux.min(numbers);
    }

    public Mono<Integer> findMaxElement(Flux<Integer> numbers) {
        return MathFlux.max(numbers);
    }

}
