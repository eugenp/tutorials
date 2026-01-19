package com.baeldung.kafka.admin;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.*;

import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

import com.baeldung.kafka.resetoffset.admin.ResetOffsetService;

@Testcontainers
class ResetOffsetServiceTest {

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.9.0"));
    private static String bootstrapServers;
    private static ResetOffsetService resetService;
    private static KafkaProducer<String, String> producer;
    private static KafkaConsumer<String, String> consumer;

    @BeforeAll
    static void startKafka() {
        KAFKA_CONTAINER.start();
        bootstrapServers = KAFKA_CONTAINER.getBootstrapServers();
        resetService = new ResetOffsetService(bootstrapServers);
        producer = new KafkaProducer<>(getProducerConfig());
        consumer = new KafkaConsumer<>(getConsumerConfig());
    }

    @AfterAll
    static void stopKafka() {
        resetService.close();
        KAFKA_CONTAINER.stop();
    }

    @Test
    void givenMessagesAreConsumed_whenOffsetIsReset_thenOffsetIsSetToEarliest() {
        producer.send(new ProducerRecord<>("test-topic", "msg-1"));
        producer.send(new ProducerRecord<>("test-topic", "msg-2"));
        producer.flush();

        consumer.subscribe(List.of("test-topic"));

        int consumed = 0;
        while (consumed < 2) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(2));
            consumed += records.count();
        }

        awaitCommittedOffset(2L);
        awaitGroupInactive();

        resetService.reset("test-topic", "test-group");

        awaitCommittedOffset(0L);
    }

    @Test
    void givenNoMessagesConsumed_whenOffsetIsReset_thenOffsetIsSetToEarliest() {
        producer.send(new ProducerRecord<>("test-topic", "msg-1"));
        producer.send(new ProducerRecord<>("test-topic", "msg-2"));
        producer.flush();

        awaitCommittedOffset(0L);
        awaitGroupInactive();

        resetService.reset("test-topic", "test-group");

        awaitCommittedOffset(0L);
    }


    private long fetchCommittedOffset() throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (AdminClient admin = AdminClient.create(props)) {
            Map<TopicPartition, OffsetAndMetadata> offsets = admin.listConsumerGroupOffsets("test-group")
                .partitionsToOffsetAndMetadata()
                .get();

            return offsets.values()
                .iterator()
                .next()
                .offset();
        }
    }

    private void awaitGroupInactive() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        await().atMost(10, SECONDS)
            .pollInterval(Duration.ofMillis(300))
            .untilAsserted(() -> {
                try (AdminClient admin = AdminClient.create(props)) {
                    ConsumerGroupDescription description = admin.describeConsumerGroups(List.of("test-group"))
                        .describedGroups()
                        .get("test-group")
                        .get();

                    Assertions.assertTrue(description.members()
                        .isEmpty(), "Consumer group is still active");
                }
            });
    }

    private void awaitCommittedOffset(long expectedOffset) {
        await().atMost(10, SECONDS)
            .pollInterval(Duration.ofMillis(300))
            .untilAsserted(() -> assertEquals(expectedOffset, fetchCommittedOffset()));
    }

    private static Properties getProducerConfig() {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return producerProperties;
    }

    private static Properties getConsumerConfig() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

        return props;
    }
}
