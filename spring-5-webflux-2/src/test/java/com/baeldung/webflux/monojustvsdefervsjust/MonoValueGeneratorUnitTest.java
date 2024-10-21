package com.baeldung.webflux.monojustvsdefervsjust;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class MonoValueGeneratorUnitTest {

    private MonoValueGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new MonoValueGenerator();
    }

    @Test
    void givenStaticValue_whenMonoSubscribed_thenShouldReturnStaticValue() {
        Mono<String> mono = generator.getStaticMono();

        StepVerifier.create(mono)
            .expectNext("Static Value")
            .verifyComplete();
    }

    @Test
    void givenNameIsAlice_whenMonoSubscribed_thenShouldReturnGreetingForAlice() {
        Mono<String> mono = generator.getGreetingMono("Alice");

        StepVerifier.create(mono)
            .expectNext("Hello, Alice")
            .verifyComplete();
    }

    @Test
    void givenNameIsBob_whenMonoSubscribed_thenShouldReturnGreetingForBob() {
        Mono<String> mono = generator.getGreetingMono("Bob");

        StepVerifier.create(mono)
            .expectNext("Hello, Bob")
            .verifyComplete();
    }

    @Test
    void givenSuccessScenario_whenMonoSubscribed_thenShouldReturnSuccessValue() {
        Mono<String> mono = generator.performOperation(true);

        StepVerifier.create(mono)
            .expectNext("Operation Success")
            .verifyComplete();
    }

    @Test
    void givenErrorScenario_whenMonoSubscribed_thenShouldReturnError() {
        Mono<String> mono = generator.performOperation(false);

        StepVerifier.create(mono)
            .expectErrorMatches(throwable -> throwable instanceof RuntimeException && throwable.getMessage()
                .equals("Operation Failed"))
            .verify();
    }

    @Test
    void whenUsingMonoJust_thenValueIsCreatedEagerly() {
        String[] value = { "Hello" };
        Mono<String> mono = Mono.just(value[0]);

        value[0] = "world";

        mono.subscribe(actualValue -> assertEquals("Hello", actualValue));
    }

    @Test
    void whenUsingMonoDefer_thenValueIsCreatedLazily() {
        String[] value = { "Hello" };
        Mono<String> mono = Mono.defer(() -> Mono.just(value[0]));

        value[0] = "World";

        mono.subscribe(actualValue -> assertEquals("World", actualValue));
    }

}