package com.baeldung.kafka.processor;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.kafka.config.KafkaConfig;

public class AtLeastOnceProcessor {

    private static final Logger logger = LoggerFactory.getLogger(AtLeastOnceProcessor.class);

    public void processOrdersWithGuarantees() {
        Properties props = KafkaConfig.createConsumerProperties("at-least-once-processor");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList(KafkaConfig.ORDER_EVENTS_TOPIC));

            while (!Thread.currentThread()
                .isInterrupted()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                Map<TopicPartition, OffsetAndMetadata> offsetsToCommit = new HashMap<>();

                for (ConsumerRecord<String, String> record : records) {
                    try {
                        processOrderSafely(record);
                        TopicPartition partition = new TopicPartition(record.topic(), record.partition());
                        offsetsToCommit.put(partition, new OffsetAndMetadata(record.offset() + 1));
                    } catch (Exception e) {
                        logger.error("Failed to process order at offset {}: {}", record.offset(), e.getMessage());
                        break;
                    }
                }

                if (!offsetsToCommit.isEmpty()) {
                    try {
                        consumer.commitSync(offsetsToCommit);
                        logger.info("Committed offsets for {} partitions", offsetsToCommit.size());
                    } catch (CommitFailedException e) {
                        logger.error("Failed to commit offsets: {}", e.getMessage());
                    }
                }
            }
        }
    }

    private void processOrderSafely(ConsumerRecord<String, String> record) throws Exception {
        logger.info("Processing order event safely: orderId={}, event={}", record.key(), record.value());

        if (Math.random() < 0.1) {
            throw new Exception("Simulated order processing failure");
        }

        Thread.sleep(50);
    }
}