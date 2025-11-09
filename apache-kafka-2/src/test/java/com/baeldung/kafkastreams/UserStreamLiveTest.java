package com.baeldung.kafkastreams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.*;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.utility.DockerImageName;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

// This live test requires a Docker Daemon running so that a kafka container can be created

@Testcontainers
class UserStreamLiveTest {

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.9.0"));
    private static KafkaProducer<String, User> producer;
    private static KafkaConsumer<String, Long> consumer;

    private static UserStreamService streamService;

    @BeforeAll
    static void setup() {
        KAFKA_CONTAINER.start();
        streamService = new UserStreamService();
        producer = new KafkaProducer<>(getProducerConfig());
        consumer = new KafkaConsumer<>(getConsumerConfig());
        new Thread(() -> streamService.start(KAFKA_CONTAINER.getBootstrapServers())).start();
    }

    @AfterAll
    static void destroy() {
        producer.flush();
        producer.close();
        consumer.close();
        streamService.stop();
        streamService.cleanUp();
        KAFKA_CONTAINER.stop();
    }

    @Test
    void givenValidUserIsSent_whenStreamServiceStarts_returnAggregatedCount() {
        producer.send(new ProducerRecord<>("user-topic", "x1", new User("1", "user1", "US")));
        producer.send(new ProducerRecord<>("user-topic", "x2", new User("2", "user2", "DE")));
        consumer.subscribe(List.of("users_per_country"));

        Awaitility.await()
            .atMost(30, TimeUnit.SECONDS)
            .pollInterval(Duration.ofSeconds(1))
            .untilAsserted(() -> {
                ConsumerRecords<String, Long> records = consumer.poll(Duration.ofMillis(500));
                Map<String, Long> counts = StreamSupport.stream(records.spliterator(), false)
                    .collect(Collectors.toMap(ConsumerRecord::key, ConsumerRecord::value, (a, b) -> b));

                assertTrue(counts.containsKey("US"));
                assertTrue(counts.containsKey("DE"));
                assertEquals(1L, counts.get("US"));
                assertEquals(1L, counts.get("DE"));
        });
    }

    @Test
    void givenValidAndNullUserIsSent_whenStreamServiceStarts_returnAggregatedCount() {
        producer.send(new ProducerRecord<>("user-topic", "x4", new User("4", "user4", "IE")));
        producer.send(new ProducerRecord<>("user-topic", "x5", null));

        consumer.subscribe(List.of("users_per_country"));

        Awaitility.await()
                .atMost(30, TimeUnit.SECONDS)
                .pollInterval(Duration.ofSeconds(1))
                .untilAsserted(() -> {
                    ConsumerRecords<String, Long> records = consumer.poll(Duration.ofMillis(500));
                    Map<String, Long> counts = StreamSupport.stream(records.spliterator(), false)
                      .collect(Collectors.toMap(ConsumerRecord::key, ConsumerRecord::value, (a, b) -> b));

                    assertTrue(counts.containsKey("IE"));
                    assertEquals(1L, counts.get("IE"));
                });
    }

    @Test
    void givenInvalidUserIsSent_whenStreamServiceStarts_returnAggregatedCountIsEmpty() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        KafkaProducer<String, byte[]> producer = new KafkaProducer<>(props);

        byte[] invalidJson = "{ invalid json".getBytes(StandardCharsets.UTF_8);
        producer.send(new ProducerRecord<>("user-topic", "1", invalidJson));

        consumer.subscribe(List.of("users_per_country"));
        Awaitility.await()
            .atMost(30, TimeUnit.SECONDS)
            .pollInterval(Duration.ofSeconds(1))
            .untilAsserted(() -> {
                ConsumerRecords<String, Long> records = consumer.poll(Duration.ofMillis(500));
                assertTrue(records.isEmpty());
            });
    }

    @Test
    void givenEmptyUserJsonIsSent_whenStreamServiceStarts_returnAggregatedCountIsEmpty() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        KafkaProducer<String, byte[]> producer = new KafkaProducer<>(props);
        byte[] emptyJson = "".getBytes(StandardCharsets.UTF_8);
        producer.send(new ProducerRecord<>("user-topic", "1", emptyJson));

        consumer.subscribe(List.of("users_per_country"));
        Awaitility.await()
            .atMost(30, TimeUnit.SECONDS)
            .pollInterval(Duration.ofSeconds(1))
            .untilAsserted(() -> {
                ConsumerRecords<String, Long> records = consumer.poll(Duration.ofMillis(500));
                assertTrue(records.isEmpty());
            });
    }

    private static Properties getProducerConfig() {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UserSerializer.class);

        return producerProperties;
    }

    private static Properties getConsumerConfig() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group-" + UUID.randomUUID());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return props;
    }
}
