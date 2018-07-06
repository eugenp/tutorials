package com.baeldung.micronaut.client;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class GreetingClientTest {
    private EmbeddedServer server;
    private GreetingClient client;

    @Before
    public void setup()
    {
        this.server = ApplicationContext.run(EmbeddedServer.class);
        this.client = server.getApplicationContext().getBean(GreetingClient.class);
    }

    @Test
    public void shouldReturnName() {
        String response = client.greet("Mike");
        assertEquals(response, "Hello Mike");
    }

    @After
    public void cleanup()
    {
        this.server.stop();
    }
}
