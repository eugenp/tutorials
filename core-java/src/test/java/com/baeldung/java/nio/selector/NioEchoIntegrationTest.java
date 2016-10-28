package com.baeldung.java.nio.selector;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class NioEchoIntegrationTest {

    @Test
    public void givenClient_whenServerEchosMessage_thenCorrect() throws IOException, InterruptedException {
        Process process = EchoServer.start();
        EchoClient client = EchoClient.start();
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        assertEquals("hello", resp1);
        assertEquals("world", resp2);

        process.destroy();
    }
}
