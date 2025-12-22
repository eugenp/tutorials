package com.baeldung.reactor.mapping;

import org.junit.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class MappingUnitTest {
    @Test
    public void givenInputStream_whenCallingTheMapOperator_thenItemsAreTransformed() {
        Function<String, String> mapper = String::toUpperCase;
        Flux<String> inFlux = Flux.just("baeldung", ".", "com");
        Flux<String> outFlux = inFlux.map(mapper);

        StepVerifier.create(outFlux)
                .expectNext("BAELDUNG", ".", "COM")
                .expectComplete()
                .verify();
    }

    @Test
    public void givenInputStream_whenCallingTheFlatMapOperator_thenItemsAreFlatten() {
        Function<String, Publisher<String>> mapper = s -> Flux.just(s.toUpperCase().split(""));
        Flux<String> inFlux = Flux.just("baeldung", ".", "com");
        Flux<String> outFlux = inFlux.flatMap(mapper);

        List<String> output = new ArrayList<>();
        outFlux.subscribe(output::add);
        assertThat(output).containsExactlyInAnyOrder("B", "A", "E", "L", "D", "U", "N", "G", ".", "C", "O", "M");
    }
}
