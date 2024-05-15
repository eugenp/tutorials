package com.baeldung.micronaut.helloworld.client;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class GreetingClientUnitTest {

    @Inject
    private EmbeddedApplication<?> application;

    @Inject
    private GreetingClient client;

    @Test
    public void testGreeting() {
        assertEquals(client.greet("Mike"), "Hello Mike");
    }
}
