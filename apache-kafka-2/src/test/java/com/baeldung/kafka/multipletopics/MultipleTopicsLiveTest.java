package com.baeldung.kafka.multipletopics;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

// This live test needs a Docker Daemon running so that a kafka container can be created

@Testcontainers
public class MultipleTopicsLiveTest {

    private final Logger log = LoggerFactory.getLogger(MultipleTopicsLiveTest.class);

    private static final String CARD_PAYMENTS_TOPIC = "card-payments";
    private static final String BANK_TRANSFERS_TOPIC = "bank-transfers";
    private static KafkaProducer<String, String> producer;
    private static KafkaConsumer<String, String> consumer;

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @BeforeAll
    static void setup() {
        KAFKA_CONTAINER.addExposedPort(9092);
        producer = new KafkaProducer<>(getProducerProperties());
        consumer = new KafkaConsumer<>(getConsumerProperties());
    }

    @AfterAll
    static void destroy() {
        KAFKA_CONTAINER.stop();
    }

    private static Properties getProducerProperties() {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return producerProperties;
    }

    private static Properties getConsumerProperties() {
        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "payments");
        return consumerProperties;
    }

    @Test
    void whenSendingMessagesOnTwoTopics_thenConsumerReceivesMessages() throws Exception {
        publishMessages();

        consumer.subscribe(Arrays.asList(CARD_PAYMENTS_TOPIC, BANK_TRANSFERS_TOPIC));

        int eventsProcessed = 0;
        for (ConsumerRecord<String, String> record : consumer.poll(Duration.ofSeconds(10))) {
            log.info("Event on topic={}, payload={}", record.topic(), record.value());
            eventsProcessed++;
        }

        assertThat(eventsProcessed).isEqualTo(2);
    }

    private void publishMessages() throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> cardPayment = new ProducerRecord<>(CARD_PAYMENTS_TOPIC, createCardPayment());
        producer.send(cardPayment).get();

        ProducerRecord<String, String> bankTransfer = new ProducerRecord<>(BANK_TRANSFERS_TOPIC, createBankTransfer());
        producer.send(bankTransfer).get();
    }

    private String createCardPayment() {
        return "{\"paymentReference\":\"A184028KM0013790\", \"type\":\"card\", \"amount\":\"275\", \"currency\":\"GBP\"}";
    }

    private String createBankTransfer() {
        return "{\"paymentReference\":\"19ae2-18mk73-009\", \"type\":\"bank\", \"amount\":\"150\", \"currency\":\"EUR\"}";
    }
}
