package com.baeldung.kafka.ssl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.time.Duration;
import java.util.UUID;

import static com.baeldung.kafka.ssl.KafkaConsumer.TOPIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@Slf4j
@ActiveProfiles("ssl")
@Testcontainers
@SpringBootTest(classes = KafkaSslApplication.class)
class KafkaSslApplicationLiveTest {

    private static final File KAFKA_COMPOSE_FILE = new File("src/test/resources/docker/docker-compose.yml");
    private static final String KAFKA_SERVICE = "kafka";
    private static final int SSL_PORT = 9093;

    @Container
    public DockerComposeContainer<?> container =
      new DockerComposeContainer<>(KAFKA_COMPOSE_FILE)
        .withExposedService(KAFKA_SERVICE, SSL_PORT, Wait.forListeningPort());

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaConsumer kafkaConsumer;

    @Test
    void givenSslIsConfigured_whenProducerSendsMessageOverSsl_thenConsumerReceivesOverSsl() {
        String message = generateSampleMessage();
        kafkaProducer.sendMessage(message, TOPIC);

        await().atMost(Duration.ofMinutes(2))
          .untilAsserted(() -> assertThat(kafkaConsumer.messages).containsExactly(message));
    }

    private static String generateSampleMessage() {
        return UUID.randomUUID().toString();
    }
}
