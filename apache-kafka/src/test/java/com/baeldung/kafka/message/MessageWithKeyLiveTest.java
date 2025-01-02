package com.baeldung.kafka.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
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
public class MessageWithKeyLiveTest {

    private static String TOPIC = "baeldung";
    private static int PARTITIONS = 5;
    private static short REPLICATION_FACTOR = 1;

    private static String MESSAGE_KEY = "message-key";
    private static String MESSAGE_VALUE = "Hello World";

    private static Admin admin;
    private static KafkaProducer<String, String> producer;
    private static KafkaConsumer<String, String> consumer;

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @BeforeAll
    static void setup() {
        KAFKA_CONTAINER.addExposedPort(9092);

        Properties adminProperties = new Properties();
        adminProperties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());

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

        admin = Admin.create(adminProperties);
        producer = new KafkaProducer<>(producerProperties);
        consumer = new KafkaConsumer<>(consumerProperties);

        admin.createTopics(Collections.singleton(new NewTopic(TOPIC, PARTITIONS, REPLICATION_FACTOR)));
    }

    @AfterAll
    static void destroy() {
        KAFKA_CONTAINER.stop();
    }

    @Test
    void givenAMessageWithKey_whenPublishedToKafkaAndConsumed_thenCheckForKey() throws ExecutionException, InterruptedException {

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, MESSAGE_KEY, MESSAGE_VALUE);
        Future<RecordMetadata> future = producer.send(producerRecord);

        RecordMetadata metadata = future.get();

        assertNotNull(metadata);

        consumer.subscribe(Arrays.asList(TOPIC));

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
        for (ConsumerRecord<String, String> consumerRecord : records) {
            assertEquals(MESSAGE_KEY, consumerRecord.key());
            assertEquals(MESSAGE_VALUE, consumerRecord.value());
        }
    }

    @Test
    void givenAListOfMessageWithKeys_whenPublishedToKafka_thenCheckedIfPublishedToSamePartition() throws ExecutionException, InterruptedException {

        boolean isSamePartition = true;
        int partition = 0;

        for (int i = 1; i <= 10; i++) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, MESSAGE_KEY, MESSAGE_VALUE);
            Future<RecordMetadata> future = producer.send(producerRecord);

            RecordMetadata metadata = future.get();

            assertNotNull(metadata);
            if (i == 1) {
                partition = metadata.partition();
            } else {
                if (partition != metadata.partition()) {
                    isSamePartition = false;
                }
            }
        }

        assertTrue(isSamePartition);
    }
}
