package com.atomix.exampletest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import io.atomix.AtomixClient;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.collections.DistributedMap;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ClientExampleTest {

    @Test
    public void ExampleTest() throws InterruptedException {
        AtomixClient client = AtomixClient.builder()
            .withTransport(new NettyTransport())
            .build();

        List<Address> cluster = Arrays.asList(new Address("localhost", 8700), new Address("localhsot", 8701));
        client.connect(cluster)
            .thenRun(() -> System.out.println("Client Connected"));

        Thread.sleep(5000);

        DistributedMap<Object, Object> map = client.getMap("map")
            .join();

        String value = (String) map.get("bar")
            .join();

        assertEquals("Hello world!", value);
    }

}
