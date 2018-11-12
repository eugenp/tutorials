package com.baeldung.stepverifier;

import org.junit.Test;
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

}
