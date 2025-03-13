package com.baeldung.socketchannel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class SocketChannelUnitTest {

    @Test
    void givenClientSendsObject_whenServerReceives_thenDataMatches() throws Exception {
        try (ServerSocketChannel server = ServerSocketChannel.open().bind(new InetSocketAddress(6000))) {
            int port = ((InetSocketAddress) server.getLocalAddress()).getPort();
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<MyObject> future = executor.submit(() -> {
                try (SocketChannel client = server.accept();
                     ObjectInputStream objIn = new ObjectInputStream(Channels.newInputStream(client))) {
                    return (MyObject) objIn.readObject();
                }
            });

            try (SocketChannel client = SocketChannel.open()) {
                client.configureBlocking(true);
                client.connect(new InetSocketAddress("localhost", 6000));

                // Ensure connection is fully established before writing
                while (!client.finishConnect()) {
                    Thread.sleep(10);
                }

                try (ObjectOutputStream objOut = new ObjectOutputStream(Channels.newOutputStream(client))) {
                    objOut.writeObject(new MyObject("Test User", 25));
                }
            }

            MyObject received = future.get(2, TimeUnit.SECONDS);
            assertEquals("Test User", received.getName());
            assertEquals(25, received.getAge());
            executor.shutdown();
        }
    }
}
