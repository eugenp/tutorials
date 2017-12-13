package com.baeldung.reactive;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertNotNull;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import com.baeldung.reactive.controller.Foo;

import reactor.core.publisher.Flux;

public class FluxUnitTest {

    @Test
    public void whenFluxIsConstructed_thenCorrect() {
        final Flux<Foo> flux = Flux.<Foo> create(fluxSink -> {
            while (true) {
                fluxSink.next(new Foo(System.currentTimeMillis(), randomAlphabetic(6)));
            }
        }).sample(Duration.ofSeconds(1)).log();

        flux.subscribe();

        assertNotNull(flux);
    }

}
