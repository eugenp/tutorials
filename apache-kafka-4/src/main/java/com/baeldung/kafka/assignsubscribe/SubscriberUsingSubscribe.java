package com.baeldung.kafka.assignsubscribe;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriberUsingSubscribe {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(SubscriberUsingSubscribe.class);

        // Create and Set Consumer Properties      
        Properties properties = new Properties();
        String bootstrapServer = "127.0.0.1:9092";
        String groupId = "baeldung-consumer-group";
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        // Create Kafka Consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // Subscribe Consumer to Our Topics
        String topics = "test-topic";
        consumer.subscribe(List.of(topics));

        logger.info("Waiting for messages...");
        // Poll the data
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

            for (ConsumerRecord<String, String> record : records) {
                logger.info("Value: " + record.value() + " -- Partition: " + record.partition());
            }
        }
    }

}
