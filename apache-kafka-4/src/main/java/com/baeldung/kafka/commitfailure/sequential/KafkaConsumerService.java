package com.baeldung.kafka.commitfailure.sequential;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final KafkaConsumer<String, String> consumer;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public KafkaConsumerService(Properties props, String topic) {
        this.consumer = new KafkaConsumer<>(props);
        consumer.subscribe(List.of(topic));
    }

    public void consume() {
        try {
            while (running.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                if (records.isEmpty()) {
                    continue;
                }
                log.info("Fetched records count: {}", records.count());
                records.forEach(this::simulateDBCall);
                consumer.commitSync();
            }
        } catch (WakeupException ex) {
            if (running.get()) {
                log.error("Error in the Kafka Consumer with exception {} {}", ex.getMessage(), ex, ex.getCause());
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

    private void simulateDBCall(ConsumerRecord<String, String> record) {
        try {
            log.info("Simulating a DB call - record key {} value {}", record.key(), record.value());
            Thread.sleep(100L);
        } catch (InterruptedException ex) {
            Thread.currentThread()
                .interrupt();
            throw new RuntimeException(ex);
        }
    }
}
