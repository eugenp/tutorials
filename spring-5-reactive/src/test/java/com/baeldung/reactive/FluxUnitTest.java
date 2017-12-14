package com.baeldung.reactive;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertNotNull;

import java.time.Duration;
import java.util.Random;

import org.junit.jupiter.api.Test;

import com.baeldung.reactive.model.Foo;

import reactor.core.publisher.Flux;

public class FluxUnitTest {

    @Test
    public void whenFluxIsConstructed_thenCorrect() {
        final Flux<Foo> flux = Flux.<Foo> create(fluxSink -> {
            while (true) {
                fluxSink.next(new Foo(new Random().nextLong(), randomAlphabetic(6)));
            }
        }).sample(Duration.ofSeconds(1)).log();

        flux.subscribe();

        assertNotNull(flux);
    }

}
