package com.baeldung.kafka;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

// This live test needs a running Docker instance so that a kafka container can be created

@Testcontainers
class KafkaConnectionLiveTest {

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));
    private KafkaAdminClient kafkaAdminClient;

    @BeforeEach
    void setup() {
        KAFKA_CONTAINER.addExposedPort(9092);
        this.kafkaAdminClient = new KafkaAdminClient(KAFKA_CONTAINER.getBootstrapServers());
    }

    @AfterEach
    void destroy() {
        KAFKA_CONTAINER.stop();
    }

    @Test
    void givenKafkaIsRunning_whenCheckedForConnection_thenConnectionIsVerified() throws Exception {
        boolean alive = kafkaAdminClient.verifyConnection();
        assertThat(alive).isTrue();
    }

}