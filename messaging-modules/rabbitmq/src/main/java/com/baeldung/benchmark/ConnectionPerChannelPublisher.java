package com.baeldung.benchmark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.benchmark.Worker.WorkerResult;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionPerChannelPublisher implements Callable<Long> {

    private static final Logger log = LoggerFactory.getLogger(ConnectionPerChannelPublisher.class);
    private final ConnectionFactory factory;
    private final int workerCount;
    private final int iterations;
    private final int payloadSize;

    ConnectionPerChannelPublisher(ConnectionFactory factory, int workerCount, int iterations, int payloadSize) {
        this.factory = factory;
        this.workerCount = workerCount;
        this.iterations = iterations;
        this.payloadSize = payloadSize;
    }

    public static void main(String[] args) {

        if (args.length != 4) {
            System.err.println("Usage: java " + ConnectionPerChannelPublisher.class.getName() + " <host> <#channels> <#messages> <payloadSize>");
            System.exit(1);
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(args[0]);

        int workerCount = Integer.parseInt(args[1]);
        int iterations = Integer.parseInt(args[2]);
        int payloadSize = Integer.parseInt(args[3]);
        
        // run the benchmark 10x and get the average throughput
        LongSummaryStatistics summary = IntStream.range(0, 9)
          .mapToObj(idx -> new ConnectionPerChannelPublisher(factory, workerCount, iterations, payloadSize))
          .map(p -> p.call())
          .collect(Collectors.summarizingLong((l) -> l));

        log.info("[I66] workers={}, throughput={}", workerCount, (int)Math.floor(summary.getAverage()));

    }

    @Override
    public Long call() {
        try {
            List<Worker> workers = new ArrayList<>();            
            CountDownLatch counter = new CountDownLatch(workerCount);

            for (int i = 0; i < workerCount; i++) {
                Connection conn = factory.newConnection();
                workers.add(new Worker("queue_" + i, conn, iterations, counter, payloadSize));
            }

            ExecutorService executor = new ThreadPoolExecutor(workerCount, workerCount, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(workerCount, true));
            long start = System.currentTimeMillis();
            log.info("[I61] Starting {} workers...", workers.size());
            executor.invokeAll(workers);
            if (counter.await(5, TimeUnit.MINUTES)) {
                long elapsed = System.currentTimeMillis() - start;
                log.info("[I59] Tasks completed: #workers={}, #iterations={}, elapsed={}ms, stats={}", workerCount, iterations, elapsed);
                return throughput(workerCount, iterations, elapsed);
            } else {
                throw new RuntimeException("[E61] Timeout waiting workers to complete");
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static long throughput(int workerCount, int iterations, long elapsed) {
        return (iterations * workerCount * 1000) / elapsed;
    }

}
