package com.baeldung.kafka.processor;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.kafka.config.KafkaConfig;

public class ExactlyOnceProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ExactlyOnceProcessor.class);

    public void processOrdersExactlyOnce() {
        Properties producerProps = KafkaConfig.createTransactionalProducerProperties("order-processor-1");
        Properties consumerProps = KafkaConfig.createConsumerProperties("exactly-once-processor");
        consumerProps.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(producerProps);
             KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps)) {
            producer.initTransactions();
            consumer.subscribe(Collections.singletonList(KafkaConfig.ORDER_EVENTS_TOPIC));

            while (!Thread.currentThread()
                .isInterrupted()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                if (!records.isEmpty()) {
                    try {
                        producer.beginTransaction();

                        for (ConsumerRecord<String, String> record : records) {
                            String processedOrder = processOrderEvent(record.value());
                            producer.send(new ProducerRecord<>(KafkaConfig.PROCESSED_ORDERS_TOPIC, record.key(), processedOrder));

                            produceDownstreamEvents(producer, record);
                        }

                        producer.sendOffsetsToTransaction(getOffsets(records), consumer.groupMetadata());
                        producer.commitTransaction();
                        logger.info("Successfully processed {} orders exactly-once", records.count());

                    } catch (Exception e) {
                        logger.error("Transaction failed: {}", e.getMessage());
                        producer.abortTransaction();
                    }
                }
            }
        }
    }

    private String processOrderEvent(String orderData) {
        return orderData + ", processed_at: " + System.currentTimeMillis() + ", processor_id: 'exactly-once-processor-1'";
    }

    private void produceDownstreamEvents(KafkaProducer<String, String> producer, ConsumerRecord<String, String> record) {
        String orderId = record.key();
        String eventData = record.value();

        if (eventData.contains("order_created")) {
            String inventoryUpdate = String.format("{'action': 'reduce_stock', 'order_id': '%s', 'timestamp': %d}", orderId, System.currentTimeMillis());
            producer.send(new ProducerRecord<>(KafkaConfig.INVENTORY_EVENTS_TOPIC, extractProductId(eventData), inventoryUpdate));
        }

        String customerNotification = String.format("{'type': 'order_update', 'order_id': '%s', 'status': 'processed', 'timestamp': %d}", orderId,
            System.currentTimeMillis());
        producer.send(new ProducerRecord<>(KafkaConfig.CUSTOMER_EVENTS_TOPIC, extractCustomerId(eventData), customerNotification));
    }

    private String extractProductId(String orderData) {
        return "product-" + Math.abs(orderData.hashCode() % 1000);
    }

    private String extractCustomerId(String orderData) {
        return "customer-" + Math.abs(orderData.hashCode() % 500);
    }

    private Map<TopicPartition, OffsetAndMetadata> getOffsets(ConsumerRecords<String, String> records) {
        Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
        for (ConsumerRecord<String, String> record : records) {
            TopicPartition partition = new TopicPartition(record.topic(), record.partition());
            offsets.put(partition, new OffsetAndMetadata(record.offset() + 1));
        }
        return offsets;
    }
}