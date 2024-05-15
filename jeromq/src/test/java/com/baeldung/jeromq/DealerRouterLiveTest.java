package com.baeldung.jeromq;

import org.junit.jupiter.api.Test;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DealerRouterLiveTest {
    @Test
    public void single() throws Exception {
        Thread brokerThread = new Thread(() -> {
            try (ZContext context = new ZContext()) {

                ZMQ.Socket broker = context.createSocket(SocketType.ROUTER);
                broker.bind("tcp://*:5555");

                String identity = broker.recvStr();
                System.out.println(Thread.currentThread().getName() + " - Received identity " + identity);

                broker.recv(0); //  Envelope delimiter
                System.out.println(Thread.currentThread().getName() + " - Received envelope");
                String message = broker.recvStr(0); //  Response from worker
                System.out.println(Thread.currentThread().getName() + " - Received message " + message);

                broker.sendMore(identity);
                broker.sendMore("xxx");
                broker.send("Hello back");
            }
        });
        brokerThread.setName("broker");

        Thread workerThread = new Thread(() -> {
            try (ZContext context = new ZContext()) {
                ZMQ.Socket worker = context.createSocket(SocketType.DEALER);
                worker.setIdentity(Thread.currentThread().getName().getBytes(ZMQ.CHARSET));

                worker.connect("tcp://localhost:5555");
                System.out.println(Thread.currentThread().getName() + " - Connected");

                worker.sendMore("");
                worker.send("Hello " + Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName() + " - Sent Hello");

                worker.recvStr(); //  Envelope delimiter
                System.out.println(Thread.currentThread().getName() + " - Received Envelope");
                String workload = worker.recvStr();
                System.out.println(Thread.currentThread().getName() + " - Received " + workload);
            }
        });
        workerThread.setName("worker");

        brokerThread.start();
        workerThread.start();

        workerThread.join();
        brokerThread.join();
    }

    @Test
    public void asynchronous() throws Exception {
        Thread brokerThread = new Thread(() -> {
            try (ZContext context = new ZContext()) {

                ZMQ.Socket broker = context.createSocket(SocketType.ROUTER);
                broker.bind("tcp://*:5555");

                while (true) {
                    String identity = broker.recvStr(ZMQ.DONTWAIT);
                    System.out.println(Thread.currentThread().getName() + " - Received identity " + identity);

                    if (identity == null) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {}
                    } else {

                        broker.recv(0); //  Envelope delimiter
                        System.out.println(Thread.currentThread().getName() + " - Received envelope");
                        String message = broker.recvStr(0); //  Response from worker
                        System.out.println(Thread.currentThread().getName() + " - Received message " + message);

                        broker.sendMore(identity);
                        broker.sendMore("xxx");
                        broker.send("Hello back");

                        break;
                    }
                }
            }
        });
        brokerThread.setName("broker");

        Thread workerThread = new Thread(() -> {
            try (ZContext context = new ZContext()) {
                ZMQ.Socket worker = context.createSocket(SocketType.DEALER);
                worker.setIdentity(Thread.currentThread().getName().getBytes(ZMQ.CHARSET));

                worker.connect("tcp://localhost:5555");
                System.out.println(Thread.currentThread().getName() + " - Connected");

                worker.sendMore("");
                worker.send("Hello " + Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getName() + " - Sent Hello");

                worker.recvStr(); //  Envelope delimiter
                System.out.println(Thread.currentThread().getName() + " - Received Envelope");
                String workload = worker.recvStr();
                System.out.println(Thread.currentThread().getName() + " - Received " + workload);
            }
        });
        workerThread.setName("worker");

        brokerThread.start();
        workerThread.start();

        workerThread.join();
        brokerThread.join();
    }


    @Test
    public void many() throws Exception {
        Thread brokerThread = new Thread(() -> {
            try (ZContext context = new ZContext()) {

                ZMQ.Socket broker = context.createSocket(SocketType.ROUTER);
                broker.bind("tcp://*:5555");

                while (!Thread.currentThread().isInterrupted()) {
                    String identity = broker.recvStr();
                    System.out.println(Thread.currentThread().getName() + " - Received identity " + identity);

                    broker.recv(0); //  Envelope delimiter
                    String message = broker.recvStr(0); //  Response from worker
                    System.out.println(Thread.currentThread().getName() + " - Received message " + message);

                    broker.sendMore(identity);
                    broker.sendMore("");
                    broker.send("Hello back to " + identity);
                }
            }
        });
        brokerThread.setName("broker");

        Set<Thread> workers = IntStream.range(0, 10)
                .mapToObj(index -> {
                    Thread workerThread = new Thread(() -> {
                        try (ZContext context = new ZContext()) {
                            ZMQ.Socket worker = context.createSocket(SocketType.DEALER);
                            worker.setIdentity(Thread.currentThread().getName().getBytes(ZMQ.CHARSET));

                            worker.connect("tcp://localhost:5555");
                            System.out.println(Thread.currentThread().getName() + " - Connected");

                            worker.sendMore("");
                            worker.send("Hello " + Thread.currentThread().getName());
                            System.out.println(Thread.currentThread().getName() + " - Sent Hello");

                            worker.recvStr(); //  Envelope delimiter
                            String workload = worker.recvStr();
                            System.out.println(Thread.currentThread().getName() + " - Received " + workload);
                        }
                    });
                    workerThread.setName("worker-" + index);

                    return workerThread;
                })
                .collect(Collectors.toSet());

        brokerThread.start();
        workers.forEach(Thread::start);

        for (Thread worker : workers) {
            worker.join();
        }
        brokerThread.interrupt();
    }

    @Test
    public void threaded() throws Exception {
        Thread brokerThread = new Thread(() -> {
            try (ZContext context = new ZContext()) {

                ZMQ.Socket broker = context.createSocket(SocketType.ROUTER);
                broker.bind("tcp://*:5555");

                ExecutorService threadPool = Executors.newFixedThreadPool(5);
                Random rng = new Random();

                while (!Thread.currentThread().isInterrupted()) {
                    String identity = broker.recvStr();
                    System.out.println(Thread.currentThread().getName() + " - Received identity " + identity);

                    broker.recv(0); //  Envelope delimiter
                    String message = broker.recvStr(0); //  Response from worker
                    System.out.println(Thread.currentThread().getName() + " - Received message " + message);

                    threadPool.submit(() -> {
                        try {
                            Thread.sleep(rng.nextInt(1000) + 1000 );
                        } catch (Exception e) {}

                        synchronized(broker) {
                            broker.sendMore(identity);
                            broker.sendMore("");
                            broker.send("Hello back to " + identity + " from " + Thread.currentThread().getName());
                        }
                    });
                }

                threadPool.shutdown();
            }
        });
        brokerThread.setName("broker");

        Set<Thread> workers = IntStream.range(0, 10)
                .mapToObj(index -> {
                    Thread workerThread = new Thread(() -> {
                        try (ZContext context = new ZContext()) {
                            ZMQ.Socket worker = context.createSocket(SocketType.DEALER);
                            worker.setIdentity(Thread.currentThread().getName().getBytes(ZMQ.CHARSET));

                            worker.connect("tcp://localhost:5555");
                            System.out.println(Thread.currentThread().getName() + " - Connected");

                            worker.sendMore("");
                            worker.send("Hello " + Thread.currentThread().getName());
                            System.out.println(Thread.currentThread().getName() + " - Sent Hello");

                            worker.recvStr(); //  Envelope delimiter
                            String workload = worker.recvStr();
                            System.out.println(Thread.currentThread().getName() + " - Received " + workload);
                        }
                    });
                    workerThread.setName("worker-" + index);

                    return workerThread;
                })
                .collect(Collectors.toSet());

        brokerThread.start();
        workers.forEach(Thread::start);

        for (Thread worker : workers) {
            worker.join();
        }
        brokerThread.interrupt();
    }
}
