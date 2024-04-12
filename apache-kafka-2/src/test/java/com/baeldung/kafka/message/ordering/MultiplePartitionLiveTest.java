package com.baeldung.kafka.message.ordering;

import com.baeldung.kafka.message.ordering.payload.UserEvent;
import com.baeldung.kafka.message.ordering.serialization.JacksonDeserializer;
import com.baeldung.kafka.message.ordering.serialization.JacksonSerializer;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.google.common.collect.ImmutableList;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Testcontainers
public class MultiplePartitionLiveTest {

    private static Admin admin;
    private static KafkaProducer<Long, UserEvent> producer;
    private static KafkaConsumer<Long, UserEvent> consumer;
    private static final Duration TIMEOUT_WAIT_FOR_MESSAGES = Duration.ofSeconds(5);

    private static Logger logger = LoggerFactory.getLogger(MultiplePartitionLiveTest.class);
    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @BeforeAll
    static void setup() throws ExecutionException, InterruptedException {
        KAFKA_CONTAINER.addExposedPort(9092);

        Properties adminProperties = new Properties();
        adminProperties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());

        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonSerializer.class.getName());

        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProperties.put(Config.CONSUMER_VALUE_DESERIALIZER_SERIALIZED_CLASS, UserEvent.class);
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        admin = Admin.create(adminProperties);
        producer = new KafkaProducer<>(producerProperties);
        consumer = new KafkaConsumer<>(consumerProperties);
        admin.createTopics(ImmutableList.of(new NewTopic(Config.MULTI_PARTITION_TOPIC, Config.MULTIPLE_PARTITIONS, Config.REPLICATION_FACTOR)))
            .all()
            .get();
    }

    @AfterAll
    static void destroy() {
        KAFKA_CONTAINER.stop();
    }

    @Test
    void givenMultiplePartitions_whenPublishedToKafkaAndConsumed_thenCheckForMessageOrder() throws ExecutionException, InterruptedException {
        List<UserEvent> sentUserEventList = new ArrayList<>();
        List<UserEvent> receivedUserEventList = new ArrayList<>();
        for (long sequenceNumber = 1; sequenceNumber <= 10; sequenceNumber++) {
            UserEvent userEvent = new UserEvent(UUID.randomUUID()
                .toString());
            userEvent.setGlobalSequenceNumber(sequenceNumber);
            userEvent.setEventNanoTime(System.nanoTime());
            Future<RecordMetadata> future = producer.send(new ProducerRecord<>(Config.MULTI_PARTITION_TOPIC, sequenceNumber, userEvent));
            sentUserEventList.add(userEvent);
            RecordMetadata metadata = future.get();
            logger.info("User Event ID: " + userEvent.getUserEventId() + ", Partition : " + metadata.partition());
        }

        consumer.subscribe(Collections.singletonList(Config.MULTI_PARTITION_TOPIC));
        ConsumerRecords<Long, UserEvent> records = consumer.poll(TIMEOUT_WAIT_FOR_MESSAGES);
        records.forEach(record -> {
            UserEvent userEvent = record.value();
            receivedUserEventList.add(userEvent);
            logger.info("User Event ID: " + userEvent.getUserEventId());
        });
        assertThat(receivedUserEventList).isNotEqualTo(sentUserEventList)
            .containsExactlyInAnyOrderElementsOf(sentUserEventList);
    }
}
