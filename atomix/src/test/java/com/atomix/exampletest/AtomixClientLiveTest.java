package com.atomix.exampletest;

import io.atomix.AtomixClient;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class AtomixClientLiveTest {

    private final AtomixClient client = AtomixClient.builder()
      .withTransport(new NettyTransport())
      .build();

    @Test
    public void whenBootstrap_thenShouldGet() throws InterruptedException, ExecutionException {
        List<Address> cluster = Arrays.asList(
          new Address("localhost", 8700),
          new Address("localhsot", 8701));

        String value = client.connect(cluster)
          .thenRun(() -> System.out.println("Client Connected"))
          .thenCompose(c -> client.getMap("map"))
          .thenCompose(m -> m.get("bar"))
          .thenApply(a -> (String) a)
          .get();

        assertEquals("Hello world!", value);
    }
}
