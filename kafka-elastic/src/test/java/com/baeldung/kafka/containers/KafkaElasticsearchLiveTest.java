package com.baeldung.kafka;

import com.baeldung.kafka.entity.NotificationEntity;
import com.baeldung.kafka.model.NotificationModel;
import com.baeldung.kafka.service.ElasticsearchService;
import com.baeldung.kafka.service.KafkaProducerService;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.test.context.EmbeddedKafka;

import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringJUnitConfig
@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { KafkaTopicConfig.TOPIC_NAME })
@EnableKafka
public class KafkaElasticsearchLiveTest {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Container
    public static ElasticsearchContainer elasticsearchContainer = new ElasticsearchContainer(
        DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:8.6.0"))
        .withExposedPorts(9200, 9300)
        .withEnv("discovery.type", "single-node");

    @BeforeAll
    public static void setUp() {
        elasticsearchContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        elasticsearchContainer.stop();
    }

    @DynamicPropertySource
    static void elasticsearchProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.elasticsearch.rest.uris", () -> elasticsearchContainer.getHttpHostAddress());
    }

    @Test
    public void givenKafkaAndElasticSetupThenCheckIntegration() {
        NotificationModel notificationModel = new NotificationModel(1, "Test message", 2);

        // Send the message to Kafka
        kafkaProducerService.sendMessage(notificationModel);

        // Simulate a wait period to allow the message to be consumed and processed
        await().atMost(5, SECONDS);

        // Check if the data was saved in Elasticsearch
        NotificationEntity savedEntity = elasticsearchService.saveData(new NotificationEntity(1, "Test message", 2));

        assertThat(savedEntity.getMessage()).isEqualTo("Test message");
    }
}
