package com.baeldung.webclientretrievevsexchange;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Benchmark;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3, time = 10, timeUnit = TimeUnit.MICROSECONDS)
@Measurement(iterations = 3, time = 10, timeUnit = TimeUnit.MICROSECONDS)
public class RetrieveAndExchangeBenchmark {
    private WebClient client;

    @Setup
    public void setup() {
        this.client = WebClient.create("https://jsonplaceholder.typicode.com/users");
    }

    @Benchmark
    public Mono<User> retrieveOneUserUsingRetrieveMethod() {
        return client.get()
            .uri("/1")
            .retrieve()
            .bodyToMono(User.class)
            .onErrorResume(Mono::error);

    }

    @Benchmark
    public Flux<User> retrieveManyUserUsingRetrieveMethod() {
        return client.get()
            .retrieve()
            .bodyToFlux(User.class)
            .onErrorResume(Flux::error);
    }

    @Benchmark
    public Mono<User> retrieveOneUserUsingExchangeToMono() {
        return client.get()
            .uri("/1")
            .exchangeToMono(res -> res.bodyToMono(User.class))
            .onErrorResume(Mono::error);
    }

    @Benchmark
    public Flux<User> retrieveManyUserUsingExchangeToFlux() {
        return client.get()
            .exchangeToFlux(res -> res.bodyToFlux(User.class))
            .onErrorResume(Flux::error);

    }

}