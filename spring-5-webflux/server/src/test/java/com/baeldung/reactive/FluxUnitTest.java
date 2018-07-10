package com.baeldung.reactive;

import com.baeldung.reactive.model.Foo;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertNotNull;

public class FluxUnitTest {

    public static final Random RANDOM = new Random();

    @Test
    public void whenFluxIsConstructed_thenCorrect() {
        final Flux<Foo> flux = Flux.<Foo> create(fluxSink -> {
            for (int i = 0 ; i < 100 ; i++) {
                fluxSink.next(new Foo(RANDOM.nextLong(), randomAlphabetic(6)));
            }
        }).sample(Duration.ofSeconds(1)).log();

        flux.subscribe();

        assertNotNull(flux);
    }

}
