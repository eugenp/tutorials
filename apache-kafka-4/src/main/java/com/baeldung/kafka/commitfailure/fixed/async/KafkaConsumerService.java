package com.baeldung.kafka.commitfailure.fixed.async;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.StreamSupport;

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
    private final KafkaConsumer<String, String> consumer;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final ExecutorService workers;
    private final Map<TopicPartition, AtomicLong> committableOffsets = new ConcurrentHashMap<>();

    public KafkaConsumerService(Properties consumerProps, String topic) {
        this.consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(List.of(topic), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                try {
                    commitSafeOffsets();
                } catch (CommitFailedException ex) {
                    log.error("Commit failed during rebalance", ex);
                }
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                partitions.forEach(committableOffsets::remove);
            }
        });
        workers = Executors.newVirtualThreadPerTaskExecutor();
    }

    public void consume() {
        try {
            while (running.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                if (records.isEmpty()) {
                    continue;
                }

                log.info("Fetched records count: {}", records.count());
                List<CompletableFuture<Void>> futures = StreamSupport.stream(records.spliterator(), false)
                    .map(record -> CompletableFuture.runAsync(() -> simulateDBUpdate(record), workers)
                        .whenComplete((ignored, ex) -> {
                            if (ex == null) {
                                markComplete(record);
                            } else {
                                log.error("Failed offset=%d send to DLQ key={} {} {}", record.offset(), record.key(), ex.getMessage());
                            }
                        })
                        .exceptionally(ex -> null))
                    .toList();

                try {
                    CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new))
                        .orTimeout(800, TimeUnit.SECONDS)
                        .join();
                } catch (CompletionException ex) {
                    log.error("Batch processing timed out or failed, committing partial offsets", ex);
                }

                commitSafeOffsets();
            }
        } catch (WakeupException ex) {
            if (running.get()) {
                log.error("Error in the Kafka Consumer with exception {} {}", ex.getMessage(), ex, ex.getCause());
                throw ex;
            }
        } finally {
            try {
                commitSafeOffsets();
            } catch (Exception ex) {
                log.warn("Final commit before close failed", ex);
            }
            consumer.close();
        }
    }

    public void shutdown() {
        running.compareAndSet(true, false);
        consumer.wakeup();
        try {
            workers.shutdown();
            if (!workers.awaitTermination(60, TimeUnit.SECONDS))
                workers.shutdownNow();
        } catch (InterruptedException ex) {
            workers.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private void simulateDBUpdate(ConsumerRecord<String, String> record) {
        try {
            log.info("Simulating a db call - record key {} value {}", record.key(), record.value());
            Thread.sleep(300L);
        } catch (InterruptedException ex) {
            Thread.currentThread()
                .interrupt();
            throw new RuntimeException(ex);
        }
    }

    private void markComplete(ConsumerRecord<String, String> record) {
        TopicPartition tp = new TopicPartition(record.topic(), record.partition());
        committableOffsets.computeIfAbsent(tp, k -> new AtomicLong(-1))
            .accumulateAndGet(record.offset() + 1, Math::max);
    }

    private void commitSafeOffsets() {
        Map<TopicPartition, OffsetAndMetadata> toCommit = new HashMap<>();

        committableOffsets.forEach((tp, atomicOffset) -> {
            long val = atomicOffset.get();
            if (val != -1L) {
                toCommit.put(tp, new OffsetAndMetadata(val));
            }
        });

        if (toCommit.isEmpty()) {
            return;
        }

        consumer.commitSync(toCommit);
        toCommit.keySet()
            .forEach(committableOffsets::remove);
    }
}
