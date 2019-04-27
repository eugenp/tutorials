package com.baeldung.networking.udp.broadcast;

import org.junit.After;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class BroadcastLiveTest {
    private BroadcastingClient client;

    @Test
    public void whenBroadcasting_thenDiscoverExpectedServers() throws Exception {
        int expectedServers = 4;
        initializeForExpectedServers(expectedServers);

        int serversDiscovered = client.discoverServers("hello server");
        assertEquals(expectedServers, serversDiscovered);
    }

    private void initializeForExpectedServers(int expectedServers) throws Exception {
        for (int i = 0; i < expectedServers; i++) {
            new BroadcastingEchoServer().start();
        }

        client = new BroadcastingClient(expectedServers);
    }

    @After
    public void tearDown() throws IOException {
        stopEchoServer();
        client.close();
    }

    private void stopEchoServer() throws IOException {
        client.discoverServers("end");
    }
}
