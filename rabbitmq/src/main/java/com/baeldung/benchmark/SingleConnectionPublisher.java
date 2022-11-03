package com.baeldung.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
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
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SingleConnectionPublisher implements Callable<Long> {
    
    private static final Logger log = LoggerFactory.getLogger(SingleConnectionPublisher.class);
    
    private final ConnectionFactory factory;
    private final int workerCount;
    private final int iterations;
    private final int payloadSize;    
    
    SingleConnectionPublisher(ConnectionFactory factory, int workerCount, int iterations, int payloadSize) {
        this.factory = factory;
        this.workerCount = workerCount;
        this.iterations = iterations;
        this.payloadSize = payloadSize;
    }
    
    public static void main(String[] args)  {
                
        if ( args.length != 4) {
            System.err.println("Usage: java " + SingleConnectionPublisher.class.getName() + " <host> <#channels> <#messages> <payloadSize>");
            System.exit(1);
        }
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(args[0]);
                
        int workerCount = Integer.parseInt(args[1]);
        int iterations = Integer.parseInt(args[2]);
        int payloadSize = Integer.parseInt(args[3]);
        
        LongSummaryStatistics summary = IntStream.range(0, 9)
            .mapToObj(idx -> new SingleConnectionPublisher(factory, workerCount, iterations, payloadSize))
            .map(p -> p.call())
            .collect(Collectors.summarizingLong((l) -> l));
        
        log.info("[I66] workers={}, throughput={}", workerCount, (int)Math.floor(summary.getAverage()));
        
    }
    
    @Override
    public Long call() {
        
        try {

            Connection connection = factory.newConnection();        
            CountDownLatch counter = new CountDownLatch(workerCount);
            List<Worker> workers = new ArrayList<>();
            
            for( int i = 0 ; i < workerCount ; i++ ) {
                workers.add(new Worker("queue_" + i, connection, iterations, counter,payloadSize));
            }
    
            ExecutorService executor = new ThreadPoolExecutor(workerCount, workerCount, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(workerCount, true));
            long start = System.currentTimeMillis();
            log.info("[I61] Starting {} workers...", workers.size());            
            List<Future<WorkerResult>> results = executor.invokeAll(workers);
            
            if( counter.await(5, TimeUnit.MINUTES) ) {
                long elapsed = System.currentTimeMillis() - start;
                
                LongSummaryStatistics summary = results.stream()
                  .map(f -> safeGet(f))
                  .map(r -> r.elapsed)
                  .collect(Collectors.summarizingLong((l) -> l));

                log.info("[I59] Tasks completed: #workers={}, #iterations={}, elapsed={}ms, stats={}",
                    workerCount,
                    iterations,
                    elapsed, summary);
                
                return throughput(workerCount,iterations,elapsed);                                
            }
            else {
                throw new RuntimeException("[E61] Timeout waiting workers to complete");
            }        
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private static <T> T safeGet(Future<T> f) {
        try {
            return f.get();
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private static long throughput(int workerCount, int iterations, long elapsed) {        
        return (iterations*workerCount*1000)/elapsed;
    }
}
