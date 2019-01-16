package com.baeldung.socket;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GreetServerIntegrationTest {

    private GreetClient client;

    private static int port;

    @BeforeClass
    public static void start() throws InterruptedException, IOException {
        
        // Take an available port
        ServerSocket s = new ServerSocket(0);
        port = s.getLocalPort();
        s.close();
        
        Executors.newSingleThreadExecutor()
            .submit(() -> new GreetServer().start(port));
        Thread.sleep(500);
    }

    @Before
    public void init() {
        client = new GreetClient();
        client.startConnection("127.0.0.1", port);

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
