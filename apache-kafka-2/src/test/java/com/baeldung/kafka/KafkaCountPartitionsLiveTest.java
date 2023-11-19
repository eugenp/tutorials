package com.baeldung.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Testcontainers
public class KafkaCountPartitionsLiveTest {
    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
    private final static String TOPIC = "baeldung-kafka-github";
    private final static Integer PARTITION_NUMBER = 3;
    private static KafkaProducer<String, String> producer;

    @BeforeAll
    static void setup() throws IOException, InterruptedException {
        KAFKA_CONTAINER.addExposedPort(9092);

        KAFKA_CONTAINER.execInContainer(
                "/bin/sh",
                "-c",
                "/usr/bin/kafka-topics " +
                        "--bootstrap-server localhost:9092 " +
                        "--create " +
                        "--replication-factor 1 " +
                        "--partitions " + PARTITION_NUMBER + " " +
                        "--topic " + TOPIC
        );

        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        producer = new KafkaProducer<>(producerProperties);
    }

    @AfterAll
    static void destroy() {
        KAFKA_CONTAINER.stop();
    }

    @Test
    void testPartitionsForTopic_isEqualToActualNumberAssignedDuringCreation() {
        List<PartitionInfo> info = producer.partitionsFor(TOPIC);
        Assertions.assertEquals(PARTITION_NUMBER, info.size());
    }

    @Test
    void testTopicPartitionDescription_isEqualToActualNumberAssignedDuringCreation() {
        Properties props = new Properties();
        props.put("bootstrap.servers", KAFKA_CONTAINER.getBootstrapServers());
        props.put("client.id","java-admin-client");
        props.put("request.timeout.ms", 3000);
        props.put("connections.max.idle.ms", 5000);
        try(AdminClient client = AdminClient.create(props)){
            DescribeTopicsResult describeTopicsResult = client.describeTopics(Collections.singletonList(TOPIC));
            Map<String, KafkaFuture<TopicDescription>> values = describeTopicsResult.values();
            KafkaFuture<TopicDescription> topicDescription = values.get(TOPIC);
            Assertions.assertEquals(PARTITION_NUMBER, topicDescription.get().partitions().size());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
