package com.baeldung.kafka.admin;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

// This live test needs a running Docker instance so that a kafka container can be created 

@Testcontainers
class KafkaTopicApplicationLiveTest {

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));

    private KafkaTopicApplication kafkaTopicApplication;

    @BeforeEach
    void setup() {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        kafkaTopicApplication = new KafkaTopicApplication(properties);
    }

    @Test
    void givenTopicName_whenCreateNewTopic_thenTopicIsCreated() throws Exception {
        kafkaTopicApplication.createTopic("test-topic");

        String topicCommand = "/usr/bin/kafka-topics --bootstrap-server=localhost:9092 --list";
        String stdout = KAFKA_CONTAINER.execInContainer("/bin/sh", "-c", topicCommand)
          .getStdout();

        assertThat(stdout).contains("test-topic");
    }
}
