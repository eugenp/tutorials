package com.baeldung.thrift;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CrossPlatformServiceTest {

    private CrossPlatformServiceServer server = new CrossPlatformServiceServer();

    @Before
    public void setUp() {
        new Thread(() -> server.start()).start();
        try {
            // wait for the server start up
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        server.stop();
    }

    @Test
    public void ping() {
        CrossPlatformServiceClient client = new CrossPlatformServiceClient();
        Assert.assertTrue(client.ping());
    }
}
