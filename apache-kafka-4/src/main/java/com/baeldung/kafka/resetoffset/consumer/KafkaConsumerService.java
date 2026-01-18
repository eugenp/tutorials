package com.baeldung.kafka.resetoffset.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final KafkaConsumer<String, String> consumer;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final boolean replayEnabled;
    private final long replayFromTimestamp;

    public KafkaConsumerService(Properties consumerProps, String topic, boolean replayEnabled, long replayFromTimestamp) {
        if (replayEnabled && replayFromTimestamp == 0L) {
            throw new IllegalArgumentException("replayFromTimestamp must be provided when replayEnabled=true");
        }

        this.consumer = new KafkaConsumer<>(consumerProps);
        this.replayEnabled = replayEnabled;
        this.replayFromTimestamp = replayFromTimestamp;
        ConsumerRebalanceListener replayRebalanceListener = new ReplayRebalanceListener(consumer, replayEnabled, replayFromTimestamp);

        consumer.subscribe(Collections.singletonList(topic), replayRebalanceListener);
    }

    public void start() {
        try {
            while (running.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                records.forEach(this::process);
            }
        } catch (WakeupException ex) {
            log.error("Error in the Kafka Consumer with exception {}", ex.getMessage(), ex);
        } finally {
            try {
                consumer.commitSync();
            } finally {
                consumer.close();
            }
        }
    }

    public void shutdown() {
        running.set(false);
        consumer.wakeup();
    }

    private void process(ConsumerRecord<String, String> record) {
        log.info("topic={} partition={} offset={} key={} value={}", record.topic(), record.partition(), record.offset(), record.key(), record.value());
    }
}
