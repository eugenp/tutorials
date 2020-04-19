package com.baeldung.async;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AsyncEchoIntegrationTest {

    Process server;
    AsyncEchoClient client;

    @Before
    public void setup() throws IOException, InterruptedException {
        server = AsyncEchoServer2.start();
        client = AsyncEchoClient.getInstance();
    }

    @Test
    public void givenServerClient_whenServerEchosMessage_thenCorrect() throws Exception {
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