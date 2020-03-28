package com.baeldung.mono;

import org.junit.Test;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class MonoUnitTest {
    @Test
    public void whenMonoProducesString_thenBlockAndConsume() {
        String expected = "hello world!";

        String result1 = Mono.just(expected).block();
        assertEquals(expected, result1);

        String result2 = Mono.just(expected).block(Duration.of(1000, ChronoUnit.MILLIS));
        assertEquals(expected, result2);

        Optional<String> result3 = Mono.<String>empty().blockOptional();
        assertEquals(Optional.empty(), result3);
    }

    @Test
    public void whenMonoProducesString_thenConsumeNonBlocking() {
        String expected = "hello world!";

        Mono.just(expected)
                .doOnNext(result -> assertEquals(expected, result))
                .subscribe();

        Mono.just(expected)
                .subscribe(result -> assertEquals(expected, result));

    }
}
