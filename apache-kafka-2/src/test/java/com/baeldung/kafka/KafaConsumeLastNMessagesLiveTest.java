package com.baeldung.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class KafaConsumeLastNMessagesLiveTest {

    private static String TOPIC1 = "baeldung-github";
    private static String TOPIC2 = "baeldung-blog";
    private static String MESSAGE_KEY = "message";
    private static KafkaProducer<String, String> producer;
    private static KafkaConsumer<String, String> consumer;
    private static KafkaProducer<String, String> transactionalProducer;

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @BeforeAll
    static void setup() {
        KAFKA_CONTAINER.addExposedPort(9092);

        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "ConsumerGroup1");

        Properties transactionalProducerProprieties = new Properties();
        transactionalProducerProprieties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        transactionalProducerProprieties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        transactionalProducerProprieties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        transactionalProducerProprieties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        transactionalProducerProprieties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "prod-0");

        producer = new KafkaProducer<>(producerProperties);
        consumer = new KafkaConsumer<>(consumerProperties);
        transactionalProducer = new KafkaProducer<>(transactionalProducerProprieties);
    }

    @AfterAll
    static void destroy() {
        KAFKA_CONTAINER.stop();
    }

    @Test
    void whenSeekingKafkaTopicCursorToEnd_consumerRetrievesOnlyDesiredNumberOfMessages() throws ExecutionException, InterruptedException {
        int messagesInTopic = 100;
        int messagesToRetrieve = 20;

        for (int i = 0; i < messagesInTopic; i++) {
            producer.send(new ProducerRecord<>(TOPIC1, null, MESSAGE_KEY, String.valueOf(i)))
                    .get();
        }

        TopicPartition partition = new TopicPartition(TOPIC1, 0);
        List<TopicPartition> partitions = new ArrayList<>();
        partitions.add(partition);
        consumer.assign(partitions);

        consumer.seekToEnd(partitions);
        long startIndex = consumer.position(partition) - messagesToRetrieve;
        consumer.seek(partition, startIndex);

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMinutes(1));
        int recordsReceived = 0;
        for (ConsumerRecord<String, String> record : records) {
            assertEquals(MESSAGE_KEY, record.key());
            assertTrue(Integer.parseInt(record.value()) >= (messagesInTopic - messagesToRetrieve));
            recordsReceived++;
        }

        assertEquals(messagesToRetrieve, recordsReceived);
    }

    @Test
    void havingTransactionalProducer_whenSeekingKafkaTopicCursorToEnd_consumerRetrievesLessMessages() throws ExecutionException, InterruptedException {
        int messagesInTopic = 100;
        int messagesToRetrieve = 20;

        transactionalProducer.initTransactions();
        for (int i = 0; i < messagesInTopic; i++) {
            transactionalProducer.beginTransaction();
            transactionalProducer.send(new ProducerRecord<>(TOPIC2, null, MESSAGE_KEY, String.valueOf(i)))
                    .get();
            transactionalProducer.commitTransaction();
        }

        TopicPartition partition = new TopicPartition(TOPIC2, 0);
        List<TopicPartition> partitions = new ArrayList<>();
        partitions.add(partition);
        consumer.assign(partitions);

        consumer.seekToEnd(partitions);
        long startIndex = consumer.position(partition) - messagesToRetrieve;
        consumer.seek(partition, startIndex);

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMinutes(1));
        int recordsReceived = 0;
        for (ConsumerRecord<String, String> record : records) {
            assertEquals(MESSAGE_KEY, record.key());
            assertTrue(Integer.parseInt(record.value()) >= (messagesInTopic - messagesToRetrieve));
            recordsReceived++;
        }

        assertTrue(messagesToRetrieve > recordsReceived);
    }

}
