package com.baeldung.socket;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EchoIntegrationTest {
    private static int port;

    @BeforeClass
    public static void start() throws InterruptedException, IOException {
        
        // Take an available port
        ServerSocket s = new ServerSocket(0);
        port = s.getLocalPort();
        s.close();

        Executors.newSingleThreadExecutor()
            .submit(() -> new EchoServer().start(port));
        Thread.sleep(500);
    }

    private EchoClient client = new EchoClient();

    @Before
    public void init() {
        client.startConnection("127.0.0.1", port);
    }

    @After
    public void tearDown() {
        client.stopConnection();
    }

    //

    @Test
    public void givenClient_whenServerEchosMessage_thenCorrect() {
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        String resp3 = client.sendMessage("!");
        String resp4 = client.sendMessage(".");
        assertEquals("hello", resp1);
        assertEquals("world", resp2);
        assertEquals("!", resp3);
        assertEquals("good bye", resp4);
    }

}
