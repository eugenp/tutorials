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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

import com.baeldung.kafka.resetoffset.admin.ResetOffsetService;

@Testcontainers
class ResetOffsetServiceTest {

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.9.0"));
    private static ResetOffsetService resetService;
    private static AdminClient adminClient;

    @BeforeAll
    static void startKafka() {
        KAFKA_CONTAINER.start();
        resetService = new ResetOffsetService(KAFKA_CONTAINER.getBootstrapServers());

        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        adminClient = AdminClient.create(props);
    }

    @AfterAll
    static void stopKafka() {
        adminClient.close();
        resetService.close();
        KAFKA_CONTAINER.stop();
    }

    @Test
    void givenMessagesAreConsumed_whenOffsetIsReset_thenOffsetIsSetToEarliest() {
        KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerConfig());
        producer.send(new ProducerRecord<>("test-topic-1", "msg-1"));
        producer.send(new ProducerRecord<>("test-topic-1", "msg-2"));
        producer.flush();

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getConsumerConfig("test-group-1"));
        consumer.subscribe(List.of("test-topic-1"));

        int consumed = 0;
        while (consumed < 2) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            consumed += records.count();
        }

        consumer.commitSync();
        consumer.close();

        await().atMost(5, SECONDS)
            .pollInterval(Duration.ofMillis(300))
            .untilAsserted(() -> assertEquals(2L, fetchCommittedOffset("test-group-1")));

        resetService.reset("test-topic-1", "test-group-1");

        await().atMost(5, SECONDS)
            .pollInterval(Duration.ofMillis(300))
            .untilAsserted(() -> assertEquals(0L, fetchCommittedOffset("test-group-1")));
    }

    @Test
    void givenConsumerIsStillActive_whenOffsetResetIsCalled_thenThrowRuntimeException_NoOffsetReset() {
        KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerConfig());
        producer.send(new ProducerRecord<>("test-topic-2", "msg-1"));
        producer.send(new ProducerRecord<>("test-topic-2", "msg-2"));
        producer.flush();

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getConsumerConfig("test-group-2"));
        consumer.subscribe(List.of("test-topic-2"));

        int consumed = 0;
        while (consumed < 2) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            consumed += records.count();
        }
        consumer.commitSync();

        assertThrows(RuntimeException.class, () -> resetService.reset("test-topic-2", "test-group-2"));

        await().atMost(5, SECONDS)
            .pollInterval(Duration.ofMillis(300))
            .untilAsserted(() -> assertEquals(2L, fetchCommittedOffset("test-group-2")));
    }

    private long fetchCommittedOffset(String groupId) throws ExecutionException, InterruptedException {
        Map<TopicPartition, OffsetAndMetadata> offsets = adminClient.listConsumerGroupOffsets(groupId)
            .partitionsToOffsetAndMetadata()
            .get();

        return offsets.values()
            .iterator()
            .next()
            .offset();
    }

    private static Properties getProducerConfig() {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return producerProperties;
    }

    private static Properties getConsumerConfig(String groupId) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        return props;
    }
}
