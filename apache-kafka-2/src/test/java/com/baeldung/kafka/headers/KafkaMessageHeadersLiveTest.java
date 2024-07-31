package com.baeldung.kafka.headers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

// This live test needs a Docker Daemon running so that a kafka container can be created

@Testcontainers
public class KafkaMessageHeadersLiveTest {

    private static String TOPIC = "baeldung";
    private static String MESSAGE_KEY = "message";
    private static String MESSAGE_VALUE = "Hello World";
    private static String HEADER_KEY = "website";
    private static String HEADER_VALUE = "baeldung.com";

    private static KafkaProducer<String, String> producer;
    private static KafkaConsumer<String, String> consumer;

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

        producer = new KafkaProducer<>(producerProperties);
        consumer = new KafkaConsumer<>(consumerProperties);
    }

    @AfterAll
    static void destroy() {
        KAFKA_CONTAINER.stop();
    }

    @Test
    void givenAMessageWithCustomHeaders_whenPublishedToKafkaAndConsumed_thenCheckForCustomHeaders() throws ExecutionException, InterruptedException {
        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader(HEADER_KEY, HEADER_VALUE.getBytes()));

        ProducerRecord<String, String> record1 = new ProducerRecord<>(TOPIC, null, MESSAGE_KEY, MESSAGE_VALUE, headers);
        Future<RecordMetadata> future = producer.send(record1);

        RecordMetadata metadata = future.get();

        assertNotNull(metadata);

        consumer.subscribe(Arrays.asList(TOPIC));

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMinutes(1));
        for (ConsumerRecord<String, String> record : records) {
            assertEquals(MESSAGE_KEY, record.key());
            assertEquals(MESSAGE_VALUE, record.value());

            Headers consumedHeaders = record.headers();
            assertNotNull(consumedHeaders);

            for (Header header : consumedHeaders) {
                assertEquals(HEADER_KEY, header.key());
                assertEquals(HEADER_VALUE, new String(header.value()));
            }
        }
    }
}
