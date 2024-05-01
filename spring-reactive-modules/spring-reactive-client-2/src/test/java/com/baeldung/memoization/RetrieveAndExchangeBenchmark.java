package com.baeldung.memoization;

import com.baeldung.webclientretrievevsexchange.User;
import org.openjdk.jmh.annotations.*;
import org.springframework.web.reactive.function.client.WebClient;
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
            .bodyToMono(com.baeldung.webclientretrievevsexchange.User.class)
            .onErrorResume(Mono::error);
    }

    @Benchmark
    public Mono<User> retrieveOneUserUsingRetrieveMethodCache() {
        Mono<User> cal = retrieveOneUserUsingRetrieveMethod().cache();
        Mono<User> cal2 = retrieveOneUserUsingRetrieveMethod().cache();
        return cal2;
    }

}