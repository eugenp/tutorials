package com.baeldung.benchmark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SharedConnectionPublisher {
    
    private static final Logger log = LoggerFactory.getLogger(SharedConnectionPublisher.class);
        
    
    public static void main(String[] args)  {
        
        try {
        
            if ( args.length != 6) {
                System.err.println("Usage: java " + SharedConnectionPublisher.class.getName() + " <host> <#channels> <#messages> <payloadSize> <#channels/connection> <extra work time(ms)>");
                System.exit(1);
            }
            
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(args[0]);
            
            List<Worker> workers = new ArrayList<>();
            
            int workerCount = Integer.parseInt(args[1]);
            int iterations = Integer.parseInt(args[2]);
            int payloadSize = Integer.parseInt(args[3]);
            int channelsPerConnection = Integer.parseInt(args[4]);
            long extraWork = Long.parseLong(args[5]);
            
            log.info("[I35] Creating {} worker{}...", workerCount, (workerCount > 1)?"s":"");
            
            CountDownLatch counter = new CountDownLatch(workerCount);
            
            int connCount = (workerCount + channelsPerConnection-1)/channelsPerConnection;
            List<Connection> connections = new ArrayList<>(connCount);
            for( int i =0 ; i< connCount; i++) {
                log.info("[I59] Creating connection#{}", i);
                connections.add(factory.newConnection());
            }
            
            for( int i = 0 ; i < workerCount ; i++ ) {
                workers.add(new Worker("queue_" + i, connections.get(i % connCount), iterations, counter,payloadSize,extraWork));
            }
    
            ExecutorService executor = new ThreadPoolExecutor(workerCount, workerCount, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(workerCount, true));
            long start = System.currentTimeMillis();
            log.info("[I61] Starting workers...");
            List<Future<WorkerResult>> results = executor.invokeAll(workers);
            
            log.info("[I55] Waiting workers to complete...");
            if( counter.await(5, TimeUnit.MINUTES) ) {
                long elapsed = System.currentTimeMillis() - start - (workerCount*iterations*extraWork);
                log.info("[I59] Tasks completed: #workers={}, #iterations={}, elapsed={}ms",
                  workerCount,
                  iterations,
                  elapsed);
                
                LongSummaryStatistics summary = results.stream()
                  .map(f -> safeGet(f))
                  .map(r -> r.elapsed)
                  .collect(Collectors.summarizingLong((l) -> l));
                
                log.info("[I74] stats={}", summary);
                log.info("[I79] result: workers={}, throughput={}",workerCount,throughput(workerCount,iterations,elapsed));
                
            }
            else {
                log.error("[E61] Timeout waiting workers to complete");
            }
        
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }     
    }
    
    private static long throughput(int workerCount, int iterations, long elapsed) {        
        return (iterations*workerCount*1000)/elapsed;
    }

    
    private static <T> T safeGet(Future<T> f) {
        try {
            return f.get();
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private static class WorkerResult {
        public final long elapsed;
        WorkerResult(long elapsed) {
            this.elapsed = elapsed;
        }        
    }
    
    
    private static class Worker implements Callable<WorkerResult> {
        
        private final Connection conn;
        private final Channel channel;
        private int iterations;
        private final CountDownLatch counter;
        private final String queue;
        private final byte[] payload;
        private long extraWork;
        
        Worker(String queue, Connection conn, int iterations, CountDownLatch counter,int payloadSize,long extraWork) throws IOException {
            this.conn = conn;
            this.iterations = iterations;
            this.counter = counter;
            this.queue = queue;
            this.extraWork = extraWork;
            
            channel = conn.createChannel();
            channel.queueDeclare(queue, false, false, true, null);
            
            this.payload = new byte[payloadSize];
            new Random().nextBytes(payload);
            
        }

        @Override
        public WorkerResult call() throws Exception {
            
            try {
                long start = System.currentTimeMillis(); 
                for ( int i = 0 ; i < iterations ; i++ ) {
                    channel.basicPublish("", queue, null,payload);
                    Thread.sleep(extraWork);
                }
                
                long elapsed = System.currentTimeMillis() - start - (extraWork*iterations);
                channel.queueDelete(queue);
                return new WorkerResult(elapsed);
            }
            finally {
                counter.countDown();
            }
        }        
    }
}
