package com.baeldung.socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class GreetServerTest {

    GreetClient client;

    {
        Executors.newSingleThreadExecutor().submit(() -> new GreetServer().start(6666));
    }

    @Before
    public void init() {
        client = new GreetClient();
		client.startConnection("127.0.0.1", 6666);

    }

    @Test
    public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() {
        String response = client.sendMessage("hello server");
        assertEquals("hello client", response);
    }

    @After
    public void finish() {
        client.stopConnection();
    }
}
