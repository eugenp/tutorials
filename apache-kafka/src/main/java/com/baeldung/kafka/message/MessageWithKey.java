package com.baeldung.kafka.message;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageWithKey {

    private static Logger logger = LoggerFactory.getLogger(MessageWithKey.class);

    private static String TOPIC = "baeldung";
    private static int PARTITIONS = 5;
    private static short REPLICATION_FACTOR = 1;

    private static String MESSAGE_KEY = "message-key";

    private static Admin admin;
    private static KafkaProducer<String, String> producer;
    private static KafkaConsumer<String, String> consumer;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        setup();

        publishMessagesWithoutKey();

        consumeMessages();

        publishMessagesWithKey();

        consumeMessages();
    }

    private static void consumeMessages() {
        consumer.subscribe(Arrays.asList(TOPIC));

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
        for (ConsumerRecord<String, String> record : records) {
            logger.info("Key : {}, Value : {}", record.key(), record.value());
        }
    }

    private static void publishMessagesWithKey() throws ExecutionException, InterruptedException {
        for (int i = 1; i <= 10; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, MESSAGE_KEY, String.valueOf(i));
            Future<RecordMetadata> future = producer.send(record);
            RecordMetadata metadata = future.get();

            logger.info(String.valueOf(metadata.partition()));
        }
    }

    private static void publishMessagesWithoutKey() throws ExecutionException, InterruptedException {
        for (int i = 1; i <= 10; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, String.valueOf(i));
            Future<RecordMetadata> future = producer.send(record);
            RecordMetadata metadata = future.get();

            logger.info(String.valueOf(metadata.partition()));
        }
    }

    private static void setup() {
        Properties adminProperties = new Properties();
        adminProperties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID()
          .toString());

        admin = Admin.create(adminProperties);
        producer = new KafkaProducer<>(producerProperties);
        consumer = new KafkaConsumer<>(consumerProperties);

        admin.createTopics(Collections.singleton(new NewTopic(TOPIC, PARTITIONS, REPLICATION_FACTOR)));
    }

}