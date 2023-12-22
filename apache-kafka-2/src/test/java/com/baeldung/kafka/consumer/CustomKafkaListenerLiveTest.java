package com.baeldung.kafka.consumer;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
class CustomKafkaListenerLiveTest {

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    static {
        Awaitility.setDefaultTimeout(ofSeconds(5L));
        Awaitility.setDefaultPollInterval(ofMillis(50L));
    }

    @Test
    void givenANewCustomKafkaListener_thenConsumesAllMessages() {
        // given
        String topic = "baeldung.articles.published";
        String bootstrapServers = KAFKA_CONTAINER.getBootstrapServers();
        List<String> consumedMessages = new ArrayList<>();

        // when
        CustomKafkaListener listener = new CustomKafkaListener(topic, bootstrapServers).onEach(consumedMessages::add);
        CompletableFuture.runAsync(listener);

        // and
        publishArticles(topic,
          "Introduction to Kafka",
          "Kotlin for Java Developers",
          "Reactive Spring Boot",
          "Deploying Spring Boot Applications",
          "Spring Security"
        );

        // then
        await().untilAsserted(() ->
          assertThat(consumedMessages).containsExactlyInAnyOrder(
            "Introduction to Kafka",
            "Kotlin for Java Developers",
            "Reactive Spring Boot",
            "Deploying Spring Boot Applications",
            "Spring Security"
          ));
    }

    private void publishArticles(String topic, String... articles) {
        try (KafkaProducer<String, String> producer = testKafkaProducer()) {
            Arrays.stream(articles)
                .map(article -> new ProducerRecord<>(topic, "key-1", article))
                .forEach(producer::send);
        }
    }

    private static KafkaProducer<String, String> testKafkaProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(props);
    }
}