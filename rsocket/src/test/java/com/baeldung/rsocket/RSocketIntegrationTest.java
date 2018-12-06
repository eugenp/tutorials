package com.baeldung.rsocket;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;

public class RSocketIntegrationTest {

    private static final Logger LOG = LoggerFactory.getLogger(RSocketIntegrationTest.class);

    private static Server server;

    public RSocketIntegrationTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        server = new Server();
    }

    @AfterClass
    public static void tearDownClass() {
        server.dispose();
    }

    @Test
    public void testRequestResponse() {
        ReqResClient client = new ReqResClient();
        String string = "Hello RSocket";
        assertEquals(string, client.callBlocking(string));
        client.dispose();
    }

    @Test
    public void testFNFAndRequestStream() {
        // create the client that pushes data to the server and start sending
        FireNForgetClient fnfClient = new FireNForgetClient();
        // get the data that is used by the client
        List<Float> data = fnfClient.getData();

        // create a client to read a stream from the server and subscribe to events
        ReqStreamClient streamClient = new ReqStreamClient();
        // assert that the data received is the same as the data sent
        Disposable subscription = streamClient.getDataStream()
                .index()
                .doOnNext(element -> assertEquals(data.get(element.getT1().intValue()), element.getT2()))
                .count()
                .subscribe(count -> assertEquals(data.size(), count.intValue()));

        // start sending the data
        fnfClient.sendData();

        // wait a short time for the data to complete then dispose everything
        try { Thread.sleep(500); } catch (Exception x) {}
        subscription.dispose();
        fnfClient.dispose();
    }

    @Test
    public void testChannel() {
        ChannelClient client = new ChannelClient();
        client.playGame();
        client.dispose();
    }

}
