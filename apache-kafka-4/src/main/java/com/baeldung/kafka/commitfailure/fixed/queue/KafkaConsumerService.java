package com.baeldung.kafka.commitfailure.fixed.queue;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
    private static final int MAX_CAPACITY = 20;
    private static final int WORKERS = 5;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final KafkaConsumer<String, String> consumer;
    private final ExecutorService workerPool;
    private final BlockingQueue<ConsumerRecord<String, String>> recordQueue;
    private final ConcurrentHashMap<TopicPartition, OffsetAndMetadata> processedOffsets = new ConcurrentHashMap<>();

    public KafkaConsumerService(Properties props, String topic) {
        this.consumer = new KafkaConsumer<>(props);
        this.consumer.subscribe(List.of(topic), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                log.info("Partitions revoked: {}, committing processed offsets", partitions);
                commitProcessed();
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                log.info("Partitions assigned: {}", partitions);
                processedOffsets.clear();
            }
        });
        this.recordQueue = new LinkedBlockingQueue<>(MAX_CAPACITY);
        this.workerPool = Executors.newVirtualThreadPerTaskExecutor();

        IntStream.range(0, WORKERS)
            .forEach((ignored) -> workerPool.submit(this::process));
    }

    public void consume() {
        try {
            while (running.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                if (records.isEmpty()) {
                    commitProcessed();
                    continue;
                }

                log.info("Fetched records count {}", records.count());

                records.forEach(record -> {
                    try {
                        recordQueue.put(record);
                    } catch (InterruptedException ex) {
                        log.error("Thread Interrupted {} {}", ex.getMessage(), ex.getStackTrace(), ex.getCause());
                        Thread.currentThread()
                            .interrupt();
                        throw new RuntimeException(ex);
                    }
                });

                commitProcessed();
            }

        } catch (WakeupException ex) {
            if (running.get()) {
                throw ex;
            }
        } finally {
            workerPool.shutdownNow();
            consumer.close();
        }
    }

    public void process() {
        while (running.get()) {
            try {
                ConsumerRecord<String, String> record = recordQueue.take();
                log.info("Fetched record {}", record);
                simulateDBUpdate(record);
                processedOffsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1));
            } catch (InterruptedException ex) {
                Thread.currentThread()
                    .interrupt();
            }
        }
    }

    public void shutdown() {
        running.set(false);
        workerPool.close();
        consumer.wakeup();
    }

    private void simulateDBUpdate(ConsumerRecord<String, String> record) {
        try {
            log.info("Simulating DB update key={} value={} offset={}", record.key(), record.value(), record.offset());
            Thread.sleep(200L);
        } catch (InterruptedException ex) {
            Thread.currentThread()
                .interrupt();
            throw new RuntimeException(ex);
        }
    }

    private void commitProcessed() {
        if (processedOffsets.isEmpty()) {
            return;
        }
        Map<TopicPartition, OffsetAndMetadata> snapshot = new HashMap<>(processedOffsets);
        processedOffsets.clear();
        try {
            consumer.commitSync(snapshot);
            log.debug("Committed offsets: {}", snapshot);
        } catch (CommitFailedException ex) {
            log.warn("Commit failed — likely rebalance in progress", ex);
        }
    }
}

