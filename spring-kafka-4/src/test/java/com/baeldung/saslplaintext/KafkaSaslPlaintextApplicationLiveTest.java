package com.baeldung.saslplaintext;

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

import static com.baeldung.saslplaintext.KafkaConsumer.TOPIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@Slf4j
@ActiveProfiles("sasl-plaintext")
@Testcontainers
@SpringBootTest(classes = KafkaSaslPlaintextApplication.class)
class KafkaSaslPlaintextApplicationLiveTest {

    private static final File KAFKA_COMPOSE_FILE = new File("src/test/resources/sasl-plaintext/docker-compose.yml");
    private static final String KAFKA_SERVICE = "kafka";
    private static final int SASL_PORT = 9092;

    @Container
    public DockerComposeContainer<?> container =
      new DockerComposeContainer<>(KAFKA_COMPOSE_FILE)
        .withExposedService(KAFKA_SERVICE, SASL_PORT, Wait.forListeningPort());

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
