package com.baeldung.jeromq;

import org.junit.jupiter.api.Test;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RequestResponseLiveTest {
    @Test
    public void requestResponse() throws Exception {
        try (ZContext context = new ZContext()) {
            Thread server = new Thread(() -> {
                ZMQ.Socket socket = context.createSocket(SocketType.REP);
                socket.bind("inproc://test");

                while (!Thread.currentThread().isInterrupted()) {
                    byte[] reply = socket.recv(0);
                    System.out.println("Server Received " + ": [" + new String(reply, ZMQ.CHARSET) + "]");

                    String response = new String(reply, ZMQ.CHARSET) + ", world";
                    socket.send(response.getBytes(ZMQ.CHARSET), 0);
                }
            });

            Thread client = new Thread(() -> {
                ZMQ.Socket socket = context.createSocket(SocketType.REQ);
                socket.connect("inproc://test");

                for (int requestNbr = 0; requestNbr != 10; requestNbr++) {
                    String request = "Hello " + requestNbr;
                    System.out.println("Sending " + request);
                    socket.send(request.getBytes(ZMQ.CHARSET), 0);

                    byte[] reply = socket.recv(0);
                    System.out.println("Client Received " + new String(reply, ZMQ.CHARSET));
                }

            });

            server.start();
            client.start();

            client.join();
            server.interrupt();
        }
    }

    @Test
    public void manyRequestResponse() throws Exception {
        try (ZContext context = new ZContext()) {
            Thread server = new Thread(() -> {
                ZMQ.Socket socket = context.createSocket(SocketType.REP);
                socket.bind("tcp://*:5555");

                while (!Thread.currentThread().isInterrupted()) {
                    byte[] reply = socket.recv(0);
                    System.out.println("Server Received " + ": [" + new String(reply, ZMQ.CHARSET) + "]");

                    String response = new String(reply, ZMQ.CHARSET) + ", world";
                    socket.send(response.getBytes(ZMQ.CHARSET), 0);
                }
            });

            Set<Thread> clients = IntStream.range(0, 10).mapToObj(index ->
                    new Thread(() -> {
                        ZMQ.Socket socket = context.createSocket(SocketType.REQ);
                        socket.connect("tcp://localhost:5555");

                        for (int requestNbr = 0; requestNbr != 10; requestNbr++) {
                            String request = "Hello " + index + " - " + requestNbr;
                            System.out.println("Sending " + request);
                            socket.send(request.getBytes(ZMQ.CHARSET), 0);

                            byte[] reply = socket.recv(0);
                            System.out.println("Client " + index + " Received " + new String(reply, ZMQ.CHARSET));
                        }

                    })
            ).collect(Collectors.toSet());

            server.start();
            clients.forEach(Thread::start);

            for (Thread client : clients) {
                client.join();
            }

            server.interrupt();
        }

    }
}
