package com.baeldung.stepverifier;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

public class TestingTestPublisherUnitTest {

    @Test
    public void testPublisher() {
        TestPublisher
          .<String>create()
          .next("First", "Second", "Third")
          .error(new RuntimeException("Message"));
    }

    @Test
    public void nonCompliant() {
        TestPublisher
          .createNoncompliant(TestPublisher.Violation.ALLOW_NULL)
          .emit("1", "2", null, "3");
    }

    @Test
    public void testPublisherInAction() {
        final TestPublisher<String> testPublisher = TestPublisher.create();

        UppercaseConverter uppercaseConverter = new UppercaseConverter(testPublisher.flux());

        StepVerifier.create(uppercaseConverter.getUpperCase())
          .then(() -> testPublisher.emit("aA", "bb", "ccc"))
          .expectNext("AA", "BB", "CCC")
          .verifyComplete();
    }

}

class UppercaseConverter {
    private final Flux<String> source;

    UppercaseConverter(Flux<String> source) {
        this.source = source;
    }

    Flux<String> getUpperCase() {
        return source
                .map(String::toUpperCase);
    }

}
