package com.baeldung.kafka.commitfailure.fixed.batch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListConsumerGroupOffsetsResult;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class KafkaConsumerServiceLiveTest {

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.9.0"));

    @Test
    void givenProducerMessagesAreSent_whenConsumerIsRunningAsBatch_thenConsumerCommitsOffset() {
        KafkaConsumerService kafkaConsumerService = new KafkaConsumerService(getConsumerConfig(), "test-topic");
        Thread th = new Thread(kafkaConsumerService::consume);
        th.start();

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerConfig())) {
            for (int num = 0; num < 300; num++) {
                producer.send(new ProducerRecord<>("test-topic", "x1" + num, "test" + num));
            }
            producer.flush();
        }

        Awaitility.await()
            .atMost(30, TimeUnit.SECONDS)
            .pollInterval(1, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                Map<TopicPartition, OffsetAndMetadata> committedOffsets;
                try (AdminClient adminClient = AdminClient.create(getAdminProps())) {
                    ListConsumerGroupOffsetsResult result = adminClient.listConsumerGroupOffsets("consumer-app");
                    committedOffsets = result.partitionsToOffsetAndMetadata()
                        .get();
                }
                assertThat(committedOffsets).isNotEmpty();
                assertNotNull(committedOffsets.get(new TopicPartition("test-topic", 0)));
                assertEquals(300L, committedOffsets.get(new TopicPartition("test-topic", 0))
                    .offset());
            });

        kafkaConsumerService.shutdown();
    }

    private static Properties getProducerConfig() {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return producerProperties;
    }

    private static Properties getConsumerConfig() {
        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-app");
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        consumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50);
        consumerProperties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 3000);

        return consumerProperties;
    }

    private static Properties getAdminProps() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        return props;
    }
}
