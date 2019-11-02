package com.baeldung.micronaut.helloworld.client;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ConcreteGreetingClientUnitTest
{
   private EmbeddedServer server;
   private ConcreteGreetingClient client;

   @Before
   public void setup()
   {
      server = ApplicationContext.run(EmbeddedServer.class);
      client = server.getApplicationContext().getBean(ConcreteGreetingClient.class);
   }

   @After
   public void cleanup()
   {
      server.stop();
   }

   @Test
   public void testGreeting() {
      assertEquals(client.greet("Mike"), "Hello Mike");
   }

   @Test
   public void testGreetingAsync() {
      assertEquals(client.greetAsync("Mike").blockingGet(), "Hello Mike");
   }
}
