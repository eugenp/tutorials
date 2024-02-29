package com.baeldung.redpanda;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
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
import org.testcontainers.containers.Network;
import org.testcontainers.redpanda.RedpandaContainer;

public class RedpandaLiveTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedpandaLiveTest.class);

    private static RedpandaContainer redpandaContainer = null;

    private static final String TOPIC_NAME = "baeldung";

    private static final Integer BROKER_PORT = 9092;

    @BeforeAll
    static void setup() throws ExecutionException, InterruptedException {
        installRedpanda();

        createTopic(TOPIC_NAME);

        publishMessages(TOPIC_NAME);
    }

    @AfterAll
    static void cleanup() {
        redpandaContainer.stop();
    }

    private static void publishMessages(String topic) throws ExecutionException, InterruptedException {
        try (final KafkaProducer<String, String> producer = createProducer()) {
            for (int i = 0; i < 10; i++) {
                publishMessage("test_msg_key_1_" + i, "How are you redpanda:" + i, topic, producer);
            }
        }
    }

    private static void installRedpanda() {
        final String DOCKER_IMAGE = "docker.redpanda.com/redpandadata/redpanda:v23.1.2";
        Network network = Network.newNetwork();
        redpandaContainer = new RedpandaContainer(DOCKER_IMAGE).withNetwork(network)
            .withNetworkAliases("redpanda")
            .withExposedPorts(BROKER_PORT);
        redpandaContainer.start();
    }

    private static AdminClient createAdminClient() {
        Properties adminProps = new Properties();
        adminProps.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, getBrokerUrl());
        return KafkaAdminClient.create(adminProps);
    }

    private static void createTopic(String topicName) {

        try (AdminClient adminClient = createAdminClient()) {
            NewTopic topic = new NewTopic(topicName, 1, (short) 1);
            adminClient.createTopics(Collections.singleton(topic));
        } catch (Exception e) {
            LOGGER.error("Error occurred during topic creation:", e);
        }
    }

    private static KafkaProducer<String, String> createProducer() {
        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getBrokerUrl());
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return new KafkaProducer<String, String>(producerProps);
    }

    private static KafkaConsumer<String, String> createConsumer() {
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getBrokerUrl());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        return new KafkaConsumer<String, String>(consumerProps);
    }

    private static void publishMessage(String msgKey, String msg, String topic, KafkaProducer<String, String> producer)
        throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, msgKey, msg);
        producer.send(record).get();
    }

    private static String getBrokerUrl() {
        return redpandaContainer.getHost() + ":" + redpandaContainer.getMappedPort(BROKER_PORT);
    }

    @Test
    void whenCreateTopic_thenSuccess() throws ExecutionException, InterruptedException {
        String topic = "test-topic";
        createTopic(topic);
        try(AdminClient adminClient = createAdminClient()) {
            assertTrue(adminClient.listTopics()
                .names()
                .get()
                .contains(topic));
        }
    }

    @Test
    void givenTopic_whenPublishMsg_thenSuccess() {
        try (final KafkaProducer<String, String> producer = createProducer()) {
            assertDoesNotThrow(() -> publishMessage("test_msg_key_2", "Hello Redpanda!", "baeldung-topic", producer));
        }
    }

    @Test
    void givenTopic_whenConsumeMessage_thenSuccess() {
        try (KafkaConsumer<String, String> kafkaConsumer = createConsumer()) {
            kafkaConsumer.subscribe(Collections.singletonList(TOPIC_NAME));

            while(true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));
                if(records.count() == 0) {
                    continue;
                }
                assertTrue(records.count() >= 1);
                break;
            }
        }
    }
}
