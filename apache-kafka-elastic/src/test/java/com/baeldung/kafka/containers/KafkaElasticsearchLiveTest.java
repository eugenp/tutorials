package com.baeldung.kafka.containers;

import com.baeldung.kafka.configs.KafkaTopicConfig;
import com.baeldung.kafka.entity.NotificationEntity;
import com.baeldung.kafka.model.NotificationModel;
import com.baeldung.kafka.service.ElasticsearchService;
import com.baeldung.kafka.service.KafkaProducerService;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;

import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.utility.DockerImageName;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

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
    public static ElasticsearchContainer elasticsearch = new ElasticsearchContainer(DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:8.5.0"))
        .withPassword("Elasticsearch@123")
        .withAccessToHost(true)
        .withExposedPorts(9200,9300)
        .withEnv("discovery.type", "single-node")
        .withEnv("xpack.security.transport.ssl.enabled", "false")
        .withEnv("xpack.security.http.ssl.enabled", "false");

    @BeforeAll
    public static void setUp() {
        elasticsearch.start();
        elasticsearch.addExposedPort(9200);
        System.out.println(elasticsearch.getHttpHostAddress());
        System.out.println(elasticsearch.getExposedPorts());
        System.setProperty("spring.elasticsearch.uris", elasticsearch.getHttpHostAddress());
    }

    @AfterAll
    public static void tearDown() {
        elasticsearch.stop();
    }

    @Test
    public void testKafkaAndElasticsearchIntegration() {
        NotificationModel notificationModel = new NotificationModel(1, "Test message", 2);

        kafkaProducerService.sendMessage(notificationModel);

        Awaitility.await().atMost(5, TimeUnit.SECONDS);

        NotificationEntity savedEntity = null;
        try{
            savedEntity = elasticsearchService.saveData(new NotificationEntity(1, "Test message", 2));
        } catch (Exception e) {
            Logger logger = org.slf4j.LoggerFactory.getLogger(KafkaElasticsearchLiveTest.class);
            logger.error("Error while saving data to Elasticsearch", e);
        }
        assertThat(savedEntity.getMessage()).isEqualTo("Test message");
    }
}
