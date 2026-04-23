package com.baeldung.reactor.completablefuturevsmono;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

class MonoUnitTest {

    @Test
    void givenMonoAssureItRunsCorrectly() {
        Mono<String> reactiveMono = Mono.fromCallable(() -> {
                Thread.sleep(1000);
                return "Reactive Data";
            })
            .subscribeOn(Schedulers.boundedElastic());

        StepVerifier.create(reactiveMono)
            .expectNext("Reactive Data")
            .verifyComplete();
    }
}