package com.baeldung.kafka;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KafkaProducerTimeOutExceptionTest {

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    private final static String TIMEOUT_EXCEPTION_CLASS = "org.apache.kafka.common.errors.TimeoutException";
    private final static String TOPIC = "baeldung-kafka-github";
    private static String MESSAGE_KEY = "message";
    private final static String TEST_MESSAGE = "Kafka Test Message";
    private final static Integer PARTITION_NUMBER = 3;

    @BeforeAll
    static void setup() throws IOException, InterruptedException {
        KAFKA_CONTAINER.addExposedPort(9092);

        KAFKA_CONTAINER.execInContainer("/bin/sh", "-c", "/usr/bin/kafka-topics " + "--bootstrap-server localhost:9092 " + "--create " +
            "--replication-factor 1 " + "--partitions " + PARTITION_NUMBER + " " + "--topic " + TOPIC);
    }

    static Properties getProducerProperties() {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.RETRIES_CONFIG, 0);
        producerProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, 1000);

        producerProperties.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 400);
        producerProperties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 300);

        producerProperties.put(ProducerConfig.LINGER_MS_CONFIG, 10);

        producerProperties.put(ProducerConfig.ACKS_CONFIG, "all");
        producerProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        producerProperties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);

        return producerProperties;
    }

    @AfterAll
    static void destroy() {
        KAFKA_CONTAINER.stop();
    }

    @Test
    void givenProducerConfigured_whenRecordSent_thenNoExceptionOccurs() throws InterruptedException, ExecutionException {
        Properties producerProperties = getProducerProperties();
        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProperties);

        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, 1, MESSAGE_KEY, TEST_MESSAGE);

        assertDoesNotThrow(() -> {
            producer.send(record)
                .get();
        });
    }

    @Test
    void givenProducerRequestTimeOutLow_whenRecordSent_thenTimeOutExceptionOccurs() throws InterruptedException, ExecutionException {
        Properties producerProperties = getProducerProperties();
        producerProperties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 5);
        producerProperties.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 1000);
        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProperties);

        String exceptionName = "";

        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, 2, MESSAGE_KEY, TEST_MESSAGE);

        Exception exception = assertThrows(Exception.class, () -> {
            producer.send(record)
                .get();
        });

        if (exception != null) {
            exceptionName = exception.getCause()
                .getClass()
                .getCanonicalName();
        }

        producer.close();

        assertThat(exceptionName)
            .isEqualTo(TIMEOUT_EXCEPTION_CLASS);

    }

    @Test
    void givenProducerLingerTimeIsLow_whenRecordSent_thenTimeOutExceptionOccurs() throws InterruptedException, ExecutionException {
        Properties producerProperties = getProducerProperties();
        producerProperties.put(ProducerConfig.LINGER_MS_CONFIG, 0);
        producerProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, 1);
        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProperties);

        String exceptionName = "";

        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, 3, MESSAGE_KEY, TEST_MESSAGE);

        Exception exception = assertThrows(Exception.class, () -> {
            producer.send(record)
                .get();
        });

        if (exception != null) {
            exceptionName = exception.getCause()
                .getClass()
                .getCanonicalName();
        }

        producer.close();

        assertThat(exceptionName)
            .isEqualTo(TIMEOUT_EXCEPTION_CLASS);

    }

    @Test
    void givenProducerLargeBatchSize_whenRecordSent_thenTimeOutExceptionOccurs() throws InterruptedException, ExecutionException {
        Properties producerProperties = getProducerProperties();
        producerProperties.put(ProducerConfig.LINGER_MS_CONFIG, 5000);
        producerProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, 10000000);
        producerProperties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 5000);
        producerProperties.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 10000);
        KafkaProducer<String, String> producer = new KafkaProducer<>(producerProperties);
        
        String exceptionName = "";
        
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, 3, MESSAGE_KEY, TEST_MESSAGE);
        
        Exception exception = assertThrows(Exception.class, () -> {
            producer.send(record)
                .get();
        });
        
        if (exception != null) {
            exceptionName = exception.getCause()
                .getClass()
                .getCanonicalName();
        }
        
        producer.close();
        
        assertThat(exceptionName)
            .isEqualTo(TIMEOUT_EXCEPTION_CLASS);

    }

}
