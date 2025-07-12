package com.baeldung.kafka.services;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.kafka.config.KafkaConfig;

public class ConsumerGroupDemo {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerGroupDemo.class);
    private final AtomicBoolean running = new AtomicBoolean(true);

    public void startMultipleGroups() {
        logger.info("Starting multiple consumer groups for fan-out pattern...");
        deployOrderProcessingService();
        startServiceGroup("order-fulfillment", this::processOrderForFulfillment);
        startServiceGroup("analytics", this::processOrderForAnalytics);
        startServiceGroup("recommendations", this::processOrderForRecommendations);
        startServiceGroup("audit", this::processOrderForCompliance);
    }

    private void deployOrderProcessingService() {
        logger.info("Deploying order processing service with 3 instances...");
        for (int i = 0; i < 3; i++) {
            OrderProcessingService service = new OrderProcessingService("instance-" + i);
            service.startProcessing();
        }
    }

    private void startServiceGroup(String groupId, Consumer<ConsumerRecord<String, String>> processor) {
        Thread serviceThread = new Thread(() -> {
            try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(KafkaConfig.createConsumerProperties(groupId))) {

                consumer.subscribe(Collections.singletonList(KafkaConfig.ORDER_EVENTS_TOPIC));
                logger.info("Started service group: {}", groupId);

                while (running.get()) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                    for (ConsumerRecord<String, String> record : records) {
                        processor.accept(record);
                    }
                    if (!records.isEmpty()) {
                        consumer.commitSync();
                    }
                }
            } catch (Exception e) {
                if (running.get()) {
                    logger.error("Error in service group {}", groupId, e);
                }
            }
        });
        serviceThread.setName("ServiceGroup-" + groupId);
        serviceThread.start();
    }

    private void processOrderForFulfillment(ConsumerRecord<String, String> record) {
        logger.info("[FULFILLMENT] Processing order event: orderId={}, event={}", record.key(), record.value());
        simulateProcessing(50);
    }

    private void processOrderForAnalytics(ConsumerRecord<String, String> record) {
        logger.info("[ANALYTICS] Recording metrics for order: orderId={}", record.key());
        simulateProcessing(30);
    }

    private void processOrderForRecommendations(ConsumerRecord<String, String> record) {
        logger.info("[RECOMMENDATIONS] Updating models with order: orderId={}", record.key());
        simulateProcessing(200);
    }

    private void processOrderForCompliance(ConsumerRecord<String, String> record) {
        logger.info("[AUDIT] Logging compliance data for order: orderId={}", record.key());
        simulateProcessing(20);
    }

    private void simulateProcessing(int processingTimeMs) {
        try {
            Thread.sleep(processingTimeMs);
        } catch (InterruptedException e) {
            Thread.currentThread()
                .interrupt();
        }
    }

    public void shutdown() {
        logger.info("Shutting down consumer group demo...");
        running.set(false);
    }
}