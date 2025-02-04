package com.baeldung.sasl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@Testcontainers
@Slf4j
public class KafkaConsumerIntegrationTest {

    private static final String TOPIC = "test-topic";

    @Container
    public static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"))
            .withExposedPorts(9093);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaConsumer kafkaConsumer;

    @DynamicPropertySource
    static void setKafkaProperties(DynamicPropertyRegistry registry) {
        String bootstrapServers = kafkaContainer.getBootstrapServers();
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @Test
    public void testKafkaListener() throws Exception {
        String testMessage = "Hello!";
        kafkaTemplate.send(TOPIC, testMessage);

        await().atMost(Duration.ofMinutes(2))
                .untilAsserted(() -> assertThat(kafkaConsumer.messages).containsExactly(testMessage));
    }
}