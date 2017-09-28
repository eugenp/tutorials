package com.atomix.example;

import java.util.Arrays;
import java.util.List;

import io.atomix.AtomixClient;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.collections.DistributedMap;

public class ClientExample {

    public static void main(String args[]) throws InterruptedException {
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
        System.out.println("Value: " + value);

    }
}