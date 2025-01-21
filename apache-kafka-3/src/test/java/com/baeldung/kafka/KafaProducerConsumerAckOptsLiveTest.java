package com.baeldung.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class KafaProducerConsumerAckOptsLiveTest {

    private static final String CONSUMER_GROUP_ID = "ConsumerGroup1";

    private static final long INVALID_OFFSET = -1;
    private static final String TOPIC = "baeldung-kafka-github";
    private static final String MESSAGE_KEY = "message";
    private static final String TEST_MESSAGE = "Kafka Test Message";
    private static final Integer PARTITION_NUMBER = 3;

    static KafkaProducer<String, String> producerack0;
    static KafkaProducer<String, String> producerack1;
    static KafkaProducer<String, String> producerackAll;

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka"));

    @BeforeAll
    static void setUp() throws IOException, InterruptedException {

        KAFKA_CONTAINER.addExposedPort(9092);

        KAFKA_CONTAINER.execInContainer("/bin/sh", "-c", "/usr/bin/kafka-topics " + "--bootstrap-server localhost:9092 " + "--create " +
            "--replication-factor 1 " + "--partitions " + PARTITION_NUMBER + " " + "--topic " + TOPIC);

        KAFKA_CONTAINER.start();

        Properties producerProperties = getProducerProperties();
        producerProperties.put(ProducerConfig.ACKS_CONFIG, "0");

        producerack0 = new KafkaProducer<>(producerProperties);

        Properties producerack1Prop = getProducerProperties();
        producerack1Prop.put(ProducerConfig.ACKS_CONFIG, "1");

        producerack1 = new KafkaProducer<>(producerack1Prop);

        Properties producerackAllProp = getProducerProperties();
        producerackAllProp.put(ProducerConfig.ACKS_CONFIG, "all");

        producerackAll = new KafkaProducer<>(producerackAllProp);

    }

    static Properties getProducerProperties() {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());

        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.RETRIES_CONFIG, 0);
        producerProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, 1000);

        producerProperties.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 10000);
        producerProperties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 300);

        producerProperties.put(ProducerConfig.LINGER_MS_CONFIG, 10);

        producerProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        producerProperties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);

        return producerProperties;
    }

    static Properties getConsumerProperties() {
        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_ID);

        return consumerProperties;
    }

    @AfterAll
    static void destroy() {
        KAFKA_CONTAINER.stop();
    }

    @Test
    @Order(1)
    void givenProducerAck0_whenProducerSendsRecord_thenDoesNotReturnOffset() throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, 0, MESSAGE_KEY, TEST_MESSAGE + "_0");
        for (int i = 0; i < 50; i++) {
            RecordMetadata metadata = producerack0.send(record)
                .get();
            assertEquals(INVALID_OFFSET, metadata.offset());
        }
    }

    @Test
    @Order(2)
    void givenProducerAck1_whenProducerSendsRecord_thenReturnsValidOffset() throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, 1, MESSAGE_KEY, TEST_MESSAGE + "_1");
        for (int i = 0; i < 50; i++) {
            RecordMetadata metadata = producerack1.send(record)
                .get();
            assertNotEquals(INVALID_OFFSET, metadata.offset());
        }
    }

    @Test
    @Order(3)
    void givenProducerAckAll_whenProducerSendsRecord_thenReturnsValidOffset() throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, 2, MESSAGE_KEY, TEST_MESSAGE + "_ALL");
        for (int i = 0; i < 50; i++) {
            RecordMetadata metadata = producerackAll.send(record)
                .get();
            assertNotEquals(INVALID_OFFSET, metadata.offset());
        }
    }

    @Test
    @Order(4)
    void whenSeekingKafkaResetConfigLatest_thenConsumerOffsetSetToLatestRecordOffset() {
        Properties consumerProperties = getConsumerProperties();
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        long expectedStartOffset = 50;
        long actualStartOffset;

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties)) {
            TopicPartition partition1 = new TopicPartition(TOPIC, 1);
            List<TopicPartition> partitions = new ArrayList<>();
            partitions.add(partition1);
            consumer.assign(partitions);
            actualStartOffset = consumer.position(partition1);
        }

        assertEquals(expectedStartOffset, actualStartOffset);
    }

    @Test
    @Order(5)
    void whenSeekingKafkaResetConfigEarliest_thenConsumerOffsetSetToZero() {
        Properties consumerProperties = getConsumerProperties();
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        long expectedStartOffset = 0;
        long actualStartOffset;

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties)) {
            TopicPartition partition2 = new TopicPartition(TOPIC, 2);
            List<TopicPartition> partitions = new ArrayList<>();
            partitions.add(partition2);
            consumer.assign(partitions);
            actualStartOffset = consumer.position(partition2);
        }

        assertEquals(expectedStartOffset, actualStartOffset);
    }
}
