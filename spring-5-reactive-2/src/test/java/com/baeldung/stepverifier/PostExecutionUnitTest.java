package com.baeldung.stepverifier;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class PostExecutionUnitTest {

    Flux<Integer> source = Flux.<Integer>create(emitter -> {
        emitter.next(1);
        emitter.next(2);
        emitter.next(3);
        emitter.complete();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        emitter.next(4);
    }).filter(number -> number % 2 == 0);

    @Test
    public void droppedElements() {
        StepVerifier.create(source)
          .expectNext(2)
          .expectComplete()
          .verifyThenAssertThat()
          .hasDropped(4)
          .tookLessThan(Duration.ofMillis(1500));
    }

}
