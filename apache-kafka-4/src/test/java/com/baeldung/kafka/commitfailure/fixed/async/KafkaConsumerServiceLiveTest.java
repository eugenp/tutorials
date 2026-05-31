package com.baeldung.kafka.commitfailure.fixed.async;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
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
    void givenProducerMessagesSent_WhenConsumerIsRunning_ThenConsumerFailsWithCommitFailedException() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicReference<Throwable> uncaughtException = new AtomicReference<>();
        KafkaConsumerService kafkaConsumerService = new KafkaConsumerService(getConsumerConfig(), "test-topic");

        Thread th = new Thread(kafkaConsumerService::consume);
        th.setUncaughtExceptionHandler((thread, ex) -> {
            uncaughtException.set(ex);
            countDownLatch.countDown();
        });
        th.start();

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(getProducerConfig())) {
            for (int num = 0; num < 100; num++) {
                producer.send(new ProducerRecord<>("test-topic", "x1" + num, "test" + num));
                producer.flush();
            }
        }

        countDownLatch.await(30, TimeUnit.SECONDS);
        assertThat(uncaughtException.get()).doesNotThrowAnyException();

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
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-async-consumer-app");
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        consumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 30);
        consumerProperties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 500);

        return consumerProperties;
    }
}
