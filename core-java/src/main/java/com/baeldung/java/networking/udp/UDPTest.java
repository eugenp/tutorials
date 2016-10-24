package com.baeldung.java.networking.udp;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UDPTest {
    EchoClient client = null;

    @Before
    public void setup() throws IOException {
        new EchoServer().start();
        client = new EchoClient();
    }

    @Test
    public void whenCanSendAndReceivePacket_thenCorrect1() {
        String echo = client.sendEcho("hello server");
        assertEquals("hello server", echo);
        echo = client.sendEcho("server is working");
        assertFalse(echo.equals("hello server"));
    }

    @After
    public void tearDown() {
        client.sendEcho("end");
        client.close();
    }
}
