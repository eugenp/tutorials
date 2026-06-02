package com.baeldung.kafka.commitfailure.batch;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final KafkaConsumer<String, String> consumer;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public KafkaConsumerService(Properties consumerProps, String topic) {
        this.consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(List.of(topic));
    }

    public void consume() {
        try {
            while (running.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(250));
                if (records.isEmpty()) {
                    continue;
                }
                simulateDBCall(records);
                consumer.commitSync();
            }
        } catch (WakeupException ex) {
            if (running.get()) {
                log.error("Error in the Kafka Consumer with exception", ex);
                throw ex;
            }
        } finally {
            consumer.close();
        }
    }

    public void shutdown() {
        running.compareAndSet(true, false);
        consumer.wakeup();
    }

    private void simulateDBCall(ConsumerRecords<String, String> records) {
        try {
            log.info("Simulating long running batch db update for records {}", records.count());
            Thread.sleep(1000L);
        } catch (InterruptedException ex) {
            Thread.currentThread()
                .interrupt();
            throw new RuntimeException(ex);
        }
    }
}
