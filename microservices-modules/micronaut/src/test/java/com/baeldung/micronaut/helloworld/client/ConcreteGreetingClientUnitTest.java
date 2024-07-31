package com.baeldung.micronaut.helloworld.client;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class ConcreteGreetingClientUnitTest {
   @Inject
   private EmbeddedApplication<?> application;
   @Inject
   private ConcreteGreetingClient client;

   @Test
   public void testGreeting() {
      assertEquals(client.greet("Mike"), "Hello Mike");
   }

   @Test
   public void testGreetingAsync() {
      assertEquals(client.greetAsync("Mike").blockingGet(), "Hello Mike");
   }
}
