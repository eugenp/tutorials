package com.baeldung.kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

import com.baeldung.kafka.resetoffset.consumer.KafkaConsumerService;

@Testcontainers
public class KafkaConsumerServiceLiveTest {
    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.9.0"));

    @BeforeAll
    static void setup() {
        KAFKA_CONTAINER.start();
    }

    @AfterAll
    static void cleanup() {
        KAFKA_CONTAINER.stop();
    }

    @Test
    void givenConsumerReplayIsEnabled_whenReplayTimestampIsProvided_thenConsumesFromTimestamp() {
        KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerConfig());
        long firstMsgTs = System.currentTimeMillis();
        producer.send(new ProducerRecord<>("test-topic-1", 0, firstMsgTs, "x1", "test1"));
        producer.flush();

        long baseTs = System.currentTimeMillis();

        long secondMsgTs = baseTs + 1L;
        producer.send(new ProducerRecord<>("test-topic-1", 0, secondMsgTs, "x2", "test2"));
        producer.flush();

        KafkaConsumerService kafkaConsumerService = new KafkaConsumerService(getConsumerConfig("test-group-1"), "test-topic-1", baseTs);
        new Thread(kafkaConsumerService::start).start();

        Awaitility.await()
            .atMost(45, TimeUnit.SECONDS)
            .pollInterval(1, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                List<String> consumed = consumeFromCommittedOffset("test-topic-1", "test-group-1");
                assertEquals(0, consumed.size());
                assertFalse(consumed.contains("test1"));
                assertFalse(consumed.contains("test2"));
            });

        kafkaConsumerService.shutdown();
    }

    @Test
    void givenProducerMessagesSent_WhenConsumerIsRunningWithReplayDisabled_ThenConsumesLatestOffset() {
        KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerConfig());
        producer.send(new ProducerRecord<>("test-topic-2", "x3", "test3"));
        producer.send(new ProducerRecord<>("test-topic-2", "x4", "test4"));
        producer.flush();

        KafkaConsumerService service = new KafkaConsumerService(getConsumerConfig("test-group-2"),
            "test-topic-2", 0L);
        new Thread(service::start).start();

        Awaitility.await()
            .atMost(45, TimeUnit.SECONDS)
            .pollInterval(1, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                List<String> consumed = consumeFromCommittedOffset("test-topic-2", "test-group-2");
                assertEquals(0, consumed.size());
                assertFalse(consumed.contains("test3"));
                assertFalse(consumed.contains("test4"));
            });

        service.shutdown();
    }

    @Test
    void givenConsumerWithReplayedDisabledRuns_whenReplayIsEnabled_WhenTimestampProvided_ThenConsumesFromTimestamp() throws InterruptedException {
        KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerConfig());
        producer.send(new ProducerRecord<>("test-topic-3", "x5", "test5"));
        producer.flush();

        KafkaConsumerService service1 = new KafkaConsumerService(getConsumerConfig("test-group-3"),
            "test-topic-3", 0L);
        new Thread(service1::start).start();
        Thread.sleep(5000);
        service1.shutdown();

        producer.send(new ProducerRecord<>("test-topic-3", "x6", "test6"));
        producer.flush();

        KafkaConsumerService service2 = new KafkaConsumerService(getConsumerConfig("test-group-3"),
            "test-topic-3", 0L);
        new Thread(service2::start).start();

        Awaitility.await()
            .atMost(45, TimeUnit.SECONDS)
            .pollInterval(1, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                List<String> consumed = consumeFromCommittedOffset("test-topic-3", "test-group-3");
                assertEquals(0, consumed.size());
                assertFalse(consumed.contains("test5"));
                assertFalse(consumed.contains("test6"));
                assertFalse(consumed.contains("test6"));
            });

        service2.shutdown();
    }

    private List<String> consumeFromCommittedOffset(String topic, String groupId) {
        List<String> values = new ArrayList<>();

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getConsumerConfig(groupId))) {
            consumer.subscribe(Collections.singleton(topic));

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(2));
            for (ConsumerRecord<String, String> r : records) {
                values.add(r.value());
            }
        }

        return values;
    }

    private static Properties getProducerConfig() {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return producerProperties;
    }

    private static Properties getConsumerConfig(String groupId) {
        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");


        return consumerProperties;
    }
}
