package com.baeldung.java.nio.selector;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EchoTest {

    @Test
    public void givenClient_whenServerEchosMessage_thenCorrect() {
        EchoClient client = EchoClient.start();
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        assertEquals("hello", resp1);
        assertEquals("world", resp2);
    }

}
