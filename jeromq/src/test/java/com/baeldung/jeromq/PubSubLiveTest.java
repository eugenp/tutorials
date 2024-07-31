package com.baeldung.jeromq;

import org.junit.jupiter.api.Test;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PubSubLiveTest {
    @Test
    public void singleSub() throws Exception {
        Thread server = new Thread(() -> {
            try (ZContext context = new ZContext()) {
                ZMQ.Socket pub = context.createSocket(SocketType.PUB);
                pub.bind("tcp://*:5555");

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {}

                System.out.println(Thread.currentThread().getName() + " - Sending");
                pub.send("Hello");
            }
        });
        server.setName("server");

        Thread client = new Thread(() -> {
            try (ZContext context = new ZContext()) {
                ZMQ.Socket sub = context.createSocket(SocketType.SUB);
                sub.connect("tcp://localhost:5555");
                System.out.println(Thread.currentThread().getName() + " - Connected");

                sub.subscribe("".getBytes());
                System.out.println(Thread.currentThread().getName() + " - Subscribed");

                String message = sub.recvStr();
                System.out.println(Thread.currentThread().getName() + " - " + message);
            }
        });
        client.setName("client");

        server.start();
        client.start();

        client.join();
        server.join();
    }


    @Test
    public void manySub() throws Exception {
        Thread server = new Thread(() -> {
            try (ZContext context = new ZContext()) {
                ZMQ.Socket pub = context.createSocket(SocketType.PUB);
                pub.bind("tcp://*:5555");

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {}

                System.out.println(Thread.currentThread().getName() + " - Sending");
                pub.send("Hello");
            }
        });
        server.setName("server");

        Set<Thread> clients = IntStream.range(0, 10)
                .mapToObj(index -> {
                    Thread client = new Thread(() -> {
                        try (ZContext context = new ZContext()) {
                            ZMQ.Socket sub = context.createSocket(SocketType.SUB);
                            sub.connect("tcp://localhost:5555");
                            System.out.println(Thread.currentThread().getName() + " - Connected");

                            sub.subscribe("".getBytes());
                            System.out.println(Thread.currentThread().getName() + " - Subscribed");

                            String message = sub.recvStr();
                            System.out.println(Thread.currentThread().getName() + " - " + message);
                        }
                    });
                    client.setName("client-" + index);

                    return client;
                })
                .collect(Collectors.toSet());


        server.start();
        clients.forEach(Thread::start);

        for (Thread client : clients) {
            client.join();
        }

        server.join();
    }
}
