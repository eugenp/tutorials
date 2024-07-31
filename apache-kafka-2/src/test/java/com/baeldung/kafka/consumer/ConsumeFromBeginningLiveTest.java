package com.baeldung.kafka.consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
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
public class ConsumeFromBeginningLiveTest {

    private static Logger logger = LoggerFactory.getLogger(ConsumeFromBeginningLiveTest.class);

    private static String TOPIC = "baeldung";
    private static int messagesInTopic = 10;

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
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID()
          .toString());

        producer = new KafkaProducer<>(producerProperties);
        consumer = new KafkaConsumer<>(consumerProperties);
    }

    private static void publishMessages() throws ExecutionException, InterruptedException {
        for (int i = 1; i <= messagesInTopic; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, String.valueOf(i));
            producer.send(record)
              .get();
        }
    }

    @AfterAll
    static void destroy() {
        KAFKA_CONTAINER.stop();
    }

    @Test
    void givenMessages_whenConsumedFromBeginning_thenCheckIfConsumedFromBeginning() throws ExecutionException, InterruptedException {

        publishMessages();

        consumer.subscribe(Arrays.asList(TOPIC));

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));

        int messageCount = 0;
        for (ConsumerRecord<String, String> record : records) {
            logger.info(record.value());
            messageCount++;
        }

        assertEquals(messagesInTopic, messageCount);

        consumer.seekToBeginning(consumer.assignment());

        records = consumer.poll(Duration.ofSeconds(10));

        messageCount = 0;
        for (ConsumerRecord<String, String> record : records) {
            logger.info(record.value());
            messageCount++;
        }

        assertEquals(messagesInTopic, messageCount);
    }
}
