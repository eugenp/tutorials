package com.baeldung.reactor.flux.parallelflux;

import org.openjdk.jmh.annotations.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class FibonacciFluxParallelFluxBenchmark {

    @Benchmark
    public List<Long> benchMarkParallelFluxSequential() {
        ParallelFlux<Long> parallelFluxFibonacci = Flux.just(43, 44, 45, 47, 48)
                .parallel(3)
                .runOn(Schedulers.parallel())
                .map(Fibonacci::fibonacci);

        return parallelFluxFibonacci.sequential().collectList().block();
    }

    @Benchmark
    public List<Long> benchMarkFluxSequential() {
        Flux<Long> fluxFibonacci = Flux.just(43, 44, 45, 47, 48)
                .map(Fibonacci::fibonacci);

        return fluxFibonacci.collectList().block();
    }
}
