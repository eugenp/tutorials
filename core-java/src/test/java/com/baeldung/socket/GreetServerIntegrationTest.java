package com.baeldung.socket;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class GreetServerIntegrationTest {

    private GreetClient client;

    private static final Integer PORT = 6666;

    @BeforeClass
    public static void start() throws InterruptedException {
        Executors.newSingleThreadExecutor()
            .submit(() -> new GreetServer().start(PORT));
        Thread.sleep(500);
    }

    @Before
    public void init() {
        client = new GreetClient();
        client.startConnection("127.0.0.1", PORT);

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
