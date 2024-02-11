package com.baeldung.spring.kafka.startstopconsumer;

import com.baeldung.spring.kafka.start.stop.consumer.*;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;
import org.testcontainers.utility.DockerImageName;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Testcontainers
@SpringBootTest(classes = StartStopConsumerApplication.class)
public class StartStopConsumerUnitTest {

    private static KafkaProducer<Long, UserEvent> producer;

    private static final Logger logger = LoggerFactory.getLogger(StartStopConsumerUnitTest.class);

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @Autowired
    KafkaListenerControlService kafkaListenerControlService;

    @Autowired
    UserEventStore userEventStore;

    @DynamicPropertySource
    static void setProps(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", KAFKA_CONTAINER::getBootstrapServers);
    }

    @BeforeAll
    static void setup() throws ExecutionException, InterruptedException {
        KAFKA_CONTAINER.addExposedPort(9092);

        Properties adminProperties = new Properties();
        adminProperties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());

        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        Admin admin = Admin.create(adminProperties);
        producer = new KafkaProducer<>(producerProperties);
        admin.createTopics(ImmutableList.of(new NewTopic(Constants.MULTI_PARTITION_TOPIC, Constants.MULTIPLE_PARTITIONS, Constants.REPLICATION_FACTOR)))
                .all()
                .get();
    }

    @AfterAll
    static void destroy() {
        KAFKA_CONTAINER.stop();
    }

    @Test
    void processMessages_whenListenerIsRestarted_thenCorrectNumberOfMessagesAreConsumed() throws ExecutionException, InterruptedException {
        kafkaListenerControlService.startListener(Constants.LISTENER_ID);

        //Verification that listener has started.
        UserEvent startUserEventTest = new UserEvent(UUID.randomUUID().toString());
        startUserEventTest.setEventNanoTime(System.nanoTime());
        producer.send(new ProducerRecord<>(Constants.MULTI_PARTITION_TOPIC, startUserEventTest));
        await().untilAsserted(() -> assertEquals(1, this.userEventStore.getUserEvents().size()));
        this.userEventStore.clearUserEvents();

        for (long count = 1; count <= 10; count++) {
            UserEvent userEvent = new UserEvent(UUID.randomUUID().toString());
            userEvent.setEventNanoTime(System.nanoTime());
            Future<RecordMetadata> future = producer.send(new ProducerRecord<>(Constants.MULTI_PARTITION_TOPIC, userEvent));
            RecordMetadata metadata = future.get();
            if (count == 4) {
                await().untilAsserted(() -> assertEquals(4, this.userEventStore.getUserEvents().size()));
                this.kafkaListenerControlService.stopListener(Constants.LISTENER_ID);
                this.userEventStore.clearUserEvents();
            }
            logger.info("User Event ID: " + userEvent.getUserEventId() + ", Partition : " + metadata.partition());
        }
        assertEquals(0, this.userEventStore.getUserEvents().size());
        kafkaListenerControlService.startListener(Constants.LISTENER_ID);
        await().untilAsserted(() -> assertEquals(6, this.userEventStore.getUserEvents().size()));
        kafkaListenerControlService.stopListener(Constants.LISTENER_ID);
    }
}