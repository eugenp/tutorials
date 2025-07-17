package com.baeldung.kafka.services;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.kafka.config.KafkaConfig;

public class OrderProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(OrderProcessingService.class);
    private final String instanceId;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public OrderProcessingService(String instanceId) {
        this.instanceId = instanceId;
    }

    public void startProcessing() {
        Thread processingThread = new Thread(this::runOrderProcessor);
        processingThread.setName("OrderProcessor-" + instanceId);
        processingThread.start();
    }

    private void runOrderProcessor() {
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(KafkaConfig.createConsumerProperties("order-processing-service"))) {
            consumer.subscribe(Collections.singletonList(KafkaConfig.ORDER_EVENTS_TOPIC));
            logger.info("[{}] Started order processing service", instanceId);
            while (running.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    processOrderEvent(record);
                }
                if (!records.isEmpty()) {
                    consumer.commitSync();
                }
            }
        } catch (Exception e) {
            if (running.get()) {
                logger.error("[{}] Error in order processing", instanceId, e);
            }
        }
        logger.info("[{}] Order processing service stopped", instanceId);
    }

    private void processOrderEvent(ConsumerRecord<String, String> record) {
        logger.info("[{}] Processing order event from partition {}: orderId={}, event={}", instanceId, record.partition(), record.key(), record.value());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread()
                .interrupt();
        }
    }

    public void stop() {
        running.set(false);
        logger.info("[{}] Stopping order processing service", instanceId);
    }
}