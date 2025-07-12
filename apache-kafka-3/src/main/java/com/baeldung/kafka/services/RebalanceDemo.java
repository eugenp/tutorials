package com.baeldung.kafka.services;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.kafka.config.KafkaConfig;

public class RebalanceDemo {

    private static final Logger logger = LoggerFactory.getLogger(RebalanceDemo.class);
    private final AtomicBoolean running = new AtomicBoolean(true);

    public void runCooperativeConsumer(String consumerId) {
        Properties props = KafkaConfig.createConsumerProperties("cooperative-demo");
        props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "org.apache.kafka.clients.consumer.CooperativeStickyAssignor");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList(KafkaConfig.ORDER_EVENTS_TOPIC), new CooperativeRebalanceListener(consumerId));
            logger.info("[{}] Started cooperative consumer", consumerId);
            while (running.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, String> record : records) {
                    logger.info("[{}] Processing order (cooperative): partition={}, orderId={}", consumerId, record.partition(), record.key());

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread()
                            .interrupt();
                    }
                }

                if (!records.isEmpty()) {
                    consumer.commitSync();
                }
            }
        } catch (Exception e) {
            if (running.get()) {
                logger.error("[{}] Error in cooperative consumer", consumerId, e);
            }
        }
        logger.info("[{}] Cooperative consumer stopped", consumerId);
    }

    private static class RebalanceListener implements ConsumerRebalanceListener {

        private final String consumerId;
        private final KafkaConsumer<String, String> consumer;
        private final Map<TopicPartition, String> partitionStates = new HashMap<>();

        public RebalanceListener(String consumerId, KafkaConsumer<String, String> consumer) {
            this.consumerId = consumerId;
            this.consumer = consumer;
        }

        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            logger.warn("[{}] REBALANCE: Partitions revoked: {}, committing current work", consumerId, partitions);

            for (TopicPartition partition : partitions) {
                String state = partitionStates.remove(partition);
                if (state != null) {
                    logger.info("[{}] Saving state for partition {}: {}", consumerId, partition.partition(), state);
                }
            }

            try {
                consumer.commitSync();
                logger.info("[{}] Successfully committed offsets before rebalance", consumerId);
            } catch (Exception e) {
                logger.error("[{}] Failed to commit offsets during rebalance", consumerId, e);
            }
        }

        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            logger.info("[{}] REBALANCE: Partitions assigned: {}", consumerId, partitions);

            for (TopicPartition partition : partitions) {
                long position = consumer.position(partition);
                partitionStates.put(partition, "initialized_at_offset_" + position);
            }
        }
    }

    private static class CooperativeRebalanceListener implements ConsumerRebalanceListener {

        private final String consumerId;

        public CooperativeRebalanceListener(String consumerId) {
            this.consumerId = consumerId;
        }

        @Override
        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            logger.info("[{}] COOPERATIVE REBALANCE: Only {} partitions revoked (others continue)", consumerId, partitions.size());
        }

        @Override
        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            logger.info("[{}] COOPERATIVE REBALANCE: Received {} new partitions", consumerId, partitions.size());
        }
    }

    public void shutdown() {
        running.set(false);
    }
}