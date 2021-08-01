package com.baeldung.reactor.exception;

import org.junit.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;
import reactor.test.StepVerifier;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ExceptionUnitTest {
    @Test
    public void givenInputStreamWithAnInvalidElement_whenAPipelineOperatorThrowsAnException_thenAnErrorIsSentDownstream() {
        Function<String, Integer> mapper = input -> {
            if (input.matches("\\D")) {
                throw new NumberFormatException();
            } else {
                return Integer.parseInt(input);
            }
        };

        Flux<String> inFlux = Flux.just("1", "1.5", "2");
        Flux<Integer> outFlux = inFlux.map(mapper);

        StepVerifier.create(outFlux)
                .expectNext(1)
                .expectError(NumberFormatException.class)
                .verify();
    }

    @Test
    public void givenInputStreamWithAnInvalidElement_whenTheHandleOperatorCallsSinkErrorMethod_thenAnErrorIsSentDownstream() {
        BiConsumer<String, SynchronousSink<Integer>> handler = (input, sink) -> {
            if (input.matches("\\D")) {
                sink.error(new NumberFormatException());
            } else {
                sink.next(Integer.parseInt(input));
            }
        };

        Flux<String> inFlux = Flux.just("1", "1.5", "2");
        Flux<Integer> outFlux = inFlux.handle(handler);

        StepVerifier.create(outFlux)
                .expectNext(1)
                .expectError(NumberFormatException.class)
                .verify();
    }

    @Test
    public void givenInputStreamWithAnInvalidElement_whenTheFlatMapOperatorCallsMonoErrorMethod_thenAnErrorIsSentDownstream() {
        Function<String, Publisher<Integer>> mapper = input -> {
            if (input.matches("\\D")) {
                return Mono.error(new NumberFormatException());
            } else {
                return Mono.just(Integer.parseInt(input));
            }
        };

        Flux<String> inFlux = Flux.just("1", "1.5", "2");
        Flux<Integer> outFlux = inFlux.flatMap(mapper);

        StepVerifier.create(outFlux)
                .expectNext(1)
                .expectError(NumberFormatException.class)
                .verify();
    }

    @Test
    public void givenInputStreamWithANullElement_whenAPipelineOperatorIsCalled_thenAnNpeIsSentDownstream() {
        Function<String, Integer> mapper = input -> {
            if (input == null) {
                return 0;
            } else {
                return Integer.parseInt(input);
            }
        };

        Flux<String> inFlux = Flux.just("1", null, "2");
        Flux<Integer> outFlux = inFlux.map(mapper);

        StepVerifier.create(outFlux)
                .expectNext(1)
                .expectError(NullPointerException.class)
                .verify();
    }
}
