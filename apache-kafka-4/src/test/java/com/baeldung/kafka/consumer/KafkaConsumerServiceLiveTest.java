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

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

import com.baeldung.kafka.resetoffset.consumer.KafkaConsumerService;

@Testcontainers
public class KafkaConsumerServiceLiveTest {
    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.9.0"));
    private static KafkaProducer<String, String> producer;

    @BeforeAll
    static void setup() {
        KAFKA_CONTAINER.start();
        producer = new KafkaProducer<>(getProducerConfig());
    }

    @AfterAll
    static void cleanup() {
        producer.close();
        KAFKA_CONTAINER.stop();
    }

    @Test
    void givenConsumerReplayIsEnabled_whenReplayTimestampIsProvided_thenConsumesFromTimestamp() {
        producer.send(new ProducerRecord<>("test-topic", "x1", "test1"));
        producer.flush();

        long baseTs = System.currentTimeMillis();
        producer.send(new ProducerRecord<>("test-topic", "x2", "test2"));
        producer.flush();

        KafkaConsumerService kafkaConsumerService = new KafkaConsumerService(getConsumerConfig("test-group-1"), "test-topic", baseTs);
        new Thread(kafkaConsumerService::start).start();

        Awaitility.await()
            .atMost(45, TimeUnit.SECONDS)
            .pollInterval(1, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                List<String> consumed = consumeFromCommittedOffset("test-group-1");
                assertEquals(0, consumed.size());
                assertFalse(consumed.contains("test1"));
                assertFalse(consumed.contains("test2"));
            });

        kafkaConsumerService.shutdown();
    }

    @Test
    void givenProducerMessagesSent_WhenConsumerIsRunningWithReplayDisabled_ThenConsumesLatestOffset() {
        producer.send(new ProducerRecord<>("test-topic", "x3", "test3"));
        producer.send(new ProducerRecord<>("test-topic", "x4", "test4"));
        producer.flush();

        KafkaConsumerService service = new KafkaConsumerService(getConsumerConfig("test-group-2"),
            "test-topic", null);
        new Thread(service::start).start();

        Awaitility.await()
            .atMost(45, TimeUnit.SECONDS)
            .pollInterval(1, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                List<String> consumed = consumeFromCommittedOffset("test-group-2");
                assertEquals(0, consumed.size());
                assertFalse(consumed.contains("test3"));
                assertFalse(consumed.contains("test4"));
            });

        service.shutdown();
    }

    @Test
    void givenConsumerWithReplayedDisabledRuns_whenReplayIsEnabled_WhenTimestampProvided_ThenConsumesFromTimestamp() throws InterruptedException {
        producer.send(new ProducerRecord<>("test-topic", "x5", "test5"));
        producer.flush();

        String groupId = "test-group-3";
        KafkaConsumerService service1 = new KafkaConsumerService(getConsumerConfig(groupId),
            "test-topic", null);
        new Thread(service1::start).start();
        Thread.sleep(5000);
        service1.shutdown();

        producer.send(new ProducerRecord<>("test-topic", "x6", "test6"));
        producer.flush();

        KafkaConsumerService service2 = new KafkaConsumerService(getConsumerConfig(groupId),
            "test-topic", null);
        new Thread(service2::start).start();

        Awaitility.await()
            .atMost(45, TimeUnit.SECONDS)
            .pollInterval(1, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                List<String> consumed = consumeFromCommittedOffset(groupId);
                assertEquals(0, consumed.size());
                assertFalse(consumed.contains("test5"));
                assertFalse(consumed.contains("test6"));
                assertFalse(consumed.contains("test6"));
            });

        service2.shutdown();
    }

    private List<String> consumeFromCommittedOffset(String groupId) {
        List<String> values = new ArrayList<>();

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getConsumerConfig(groupId))) {
            consumer.subscribe(Collections.singleton("test-topic"));

            ConsumerRecords<String, String> records = consumer.poll(java.time.Duration.ofSeconds(2));
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
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        return props;
    }
}
