package com.baeldung.stepverifier;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class TimeBasedUnitTest {

    @Test
    public void simpleExample() {
        StepVerifier
          .withVirtualTime(() -> Flux.interval(Duration.ofSeconds(1)).take(2))
          .expectSubscription()
          .expectNoEvent(Duration.ofSeconds(1))
          .expectNext(0L)
          .thenAwait(Duration.ofSeconds(1))
          .expectNext(1L)
          .verifyComplete();
    }
}
