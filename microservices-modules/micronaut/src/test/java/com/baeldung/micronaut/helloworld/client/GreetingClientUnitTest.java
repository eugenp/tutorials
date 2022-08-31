package com.baeldung.micronaut.helloworld.client;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GreetingClientUnitTest {
    private EmbeddedServer server;
    private GreetingClient client;

    @Before
    public void setup()
    {
        server = ApplicationContext.run(EmbeddedServer.class);
        client = server.getApplicationContext().getBean(GreetingClient.class);
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
}
