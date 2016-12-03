package com.baeldung.java.nio2.async;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AsyncEchoTest {

    Process server;
    AsyncEchoClient client;

    @Before
    public void setup() throws IOException, InterruptedException {
        server = AsyncEchoServer2.start();
        client = AsyncEchoClient.getInstance();
    }

    @Test
    public void givenServerClient_whenServerEchosMessage_thenCorrect() {
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        assertEquals("hello", resp1);
        assertEquals("world", resp2);
    }

    @After
    public void teardown() throws IOException {
        server.destroy();
        client.stop();
    }

}
