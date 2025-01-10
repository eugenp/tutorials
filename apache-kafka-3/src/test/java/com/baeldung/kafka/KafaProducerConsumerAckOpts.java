package com.baeldung.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
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
public class KafaProducerConsumerAckOpts {
    private final static String CONSUMER_GROUP_ID = "ConsumerGroup1";
    
    private final static String TOPIC = "baeldung-kafka-github";
    private static String MESSAGE_KEY = "message";
    private final static String TEST_MESSAGE = "Kafka Test Message";
    private final static Integer PARTITION_NUMBER = 3;

    static KafkaProducer<String, String> producerack0;
    static KafkaProducer<String, String> producerack1;
    static KafkaProducer<String, String> producerackAll;

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @BeforeAll
    static void setup() throws IOException, InterruptedException {

        KAFKA_CONTAINER.addExposedPort(9092);

        KAFKA_CONTAINER.execInContainer("/bin/sh", "-c", "/usr/bin/kafka-topics " + "--bootstrap-server localhost:9092 " + "--create " +
            "--replication-factor 1 " + "--partitions " + PARTITION_NUMBER + " " + "--topic " + TOPIC);

        KAFKA_CONTAINER.start();

        Properties producerProperties = getProducerProperties();
        producerProperties.put(ProducerConfig.ACKS_CONFIG, "0");

        producerack0 = new KafkaProducer<>(producerProperties);

        Properties producerack1Prop = getProducerProperties();
        producerProperties.put(ProducerConfig.ACKS_CONFIG, "1");

        producerack1 = new KafkaProducer<>(producerack1Prop);

        Properties producerackAllProp = getProducerProperties();
        producerProperties.put(ProducerConfig.ACKS_CONFIG, "all");

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
    void givenProducerAck0_whenProducerSendsRecord_thenReplyReceivedWithoutConfirmingWrite() throws ExecutionException, InterruptedException {

        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, 0, MESSAGE_KEY, TEST_MESSAGE + "_0");
        for (int i = 0; i < 50; i++) {
            RecordMetadata metadata = producerack0.send(record)
                .get();
            assertEquals(-1, metadata.offset());
        }
    }

    @Test
    @Order(2)
    void givenProducerAck1_whenProducerSendsRecord_thenReplyReceivedConfirmingWriteToLeader() throws ExecutionException, InterruptedException {

        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, 1, MESSAGE_KEY, TEST_MESSAGE + "_1");
        for (int i = 0; i < 50; i++) {
            RecordMetadata metadata = producerack1.send(record)
                .get();
            assertNotEquals(-1, metadata.offset());
        }
    }

    @Test
    @Order(3)
    void givenProducerAckAll_whenProducerSendsRecord_thenReplyReceivedConfirmingWriteToAllReplicas() throws ExecutionException, InterruptedException {

        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, 2, MESSAGE_KEY, TEST_MESSAGE + "_ALL");
        for (int i = 0; i < 50; i++) {
            RecordMetadata metadata = producerackAll.send(record)
                .get();
            assertNotEquals(-1, metadata.offset());
        }
    }

    @Test
    @Order(4)
    void whenSeekingKafkaResetConfigLatest_consumerRetrievesLatestMessages() throws ExecutionException, InterruptedException {
        Properties consumerProperties = getConsumerProperties();
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties)) {
            TopicPartition partition1 = new TopicPartition(TOPIC, 1);
            List<TopicPartition> partitions = new ArrayList<>();
            partitions.add(partition1);
            consumer.assign(partitions);

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMinutes(1));

            deleteConsumerGroupOffsets();

            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, 1, MESSAGE_KEY, TEST_MESSAGE + "_1");
            for (int i = 0; i < 20; i++) {
                producerack1.send(producerRecord)
                    .get();
            }

            records = consumer.poll(Duration.ofMinutes(1));

            for (ConsumerRecord<String, String> record : records) {
                assertEquals(MESSAGE_KEY, record.key());
                assertTrue(record.value()
                    .contains(TEST_MESSAGE));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(5)
    void whenSeekingKafkaResetConfigEarliest_consumerRetrievesEarliestMessages() throws ExecutionException, InterruptedException {
        Properties consumerProperties = getConsumerProperties();
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties)) {

            TopicPartition partition2 = new TopicPartition(TOPIC, 2);
            List<TopicPartition> partitions = new ArrayList<>();
            partitions.add(partition2);
            consumer.assign(partitions);

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMinutes(1));

            deleteConsumerGroupOffsets();

            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, 2, MESSAGE_KEY, TEST_MESSAGE + "_all");
            for (int i = 0; i < 20; i++) {
                producerackAll.send(producerRecord)
                    .get();
            }

            records = consumer.poll(Duration.ofMinutes(1));

            for (ConsumerRecord<String, String> record : records) {
                assertEquals(MESSAGE_KEY, record.key());
                assertTrue(record.value()
                    .contains(TEST_MESSAGE));
            }
        } catch (Exception e) {

        }
    }

    public static void deleteConsumerGroupOffsets() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        Set<TopicPartition> topicSet = new HashSet<>();
        topicSet.add(new TopicPartition(TOPIC, 0));
        topicSet.add(new TopicPartition(TOPIC, 1));
        topicSet.add(new TopicPartition(TOPIC, 2));

        try (AdminClient adminClient = AdminClient.create(props)) {
            // Delete offsets for the specified topic and group            
            adminClient.deleteConsumerGroupOffsets(CONSUMER_GROUP_ID, topicSet)
                .all()
                .get();
        } catch (Exception e) {
        }
    }
}
