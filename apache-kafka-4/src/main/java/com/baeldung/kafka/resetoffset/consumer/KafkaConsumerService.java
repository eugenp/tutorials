package com.baeldung.kafka.resetoffset.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final KafkaConsumer<String, String> consumer;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public KafkaConsumerService(Properties consumerProps, String topic, Long replayFromTimestampInEpoch) {
        this.consumer = new KafkaConsumer<>(consumerProps);
        if (replayFromTimestampInEpoch != null) {
            consumer.subscribe(List.of(topic), new ReplayRebalanceListener(consumer, replayFromTimestampInEpoch));
        } else {
            consumer.subscribe(List.of(topic));
        }
    }

    public void start() {
        try {
            while (running.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                records.forEach(record ->
                    log.info("topic={} partition={} offset={} key={} value={}", record.topic(), record.partition(),
                        record.offset(), record.key(), record.value()));
                consumer.commitSync();
            }
        } catch (WakeupException ex) {
            if (running.get()) {
                log.error("Error in the Kafka Consumer with exception {}", ex.getMessage(), ex);
                throw ex;
            }
        } finally {
            consumer.close();
        }
    }

    public void shutdown() {
        running.set(false);
        consumer.wakeup();
    }
}
