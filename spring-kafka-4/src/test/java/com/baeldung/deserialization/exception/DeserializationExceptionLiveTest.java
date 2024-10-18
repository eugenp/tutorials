package com.baeldung.deserialization.exception;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@Testcontainers
@SpringBootTest(classes = Application.class)
class DeserializationExceptionLiveTest {

    @Container
    private static KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    private static KafkaProducer<String, String> testKafkaProducer;

    @Autowired
    private EmailService emailService;

    @DynamicPropertySource
    static void setProps(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", KAFKA::getBootstrapServers);
    }

    @BeforeAll
    static void beforeAll() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        testKafkaProducer = new KafkaProducer<>(props);

        Awaitility.setDefaultTimeout(ofSeconds(5));
        Awaitility.setDefaultPollInterval(ofMillis(50));
    }

    @BeforeEach
    void beforeEach() {
        emailService.getArticles().clear();
    }

    @Test
    void whenPublishingInvalidArticleEvent_thenHandleExceptionAndContinueProcessing() {
        publishArticle("{ \"article\": \"Introduction to Kafka\" }");
        publishArticle(" !! Invalid JSON !! ");
        publishArticle("{ \"article\": \"Kafka Streams Tutorial\" }");

        await().untilAsserted(() -> assertThat(emailService.getArticles())
            .containsExactlyInAnyOrder(
                "Introduction to Kafka",
                "Kafka Streams Tutorial"
            ));
    }

    @Test
    void whenPublishingValidArticleEvent_thenProcessWithoutErrors() {
        publishArticle("{ \"article\": \"Kotlin for Java Developers\" }");
        publishArticle("{ \"article\": \"The S.O.L.I.D. Principles\" }");

        await().untilAsserted(() -> assertThat(emailService.getArticles())
          .containsExactlyInAnyOrder(
            "Kotlin for Java Developers",
            "The S.O.L.I.D. Principles"
          ));
    }


    static void publishArticle(String jsonBody) {
        ProducerRecord<String, String> record = new ProducerRecord<>("baeldung.articles.published", jsonBody);
        try {
            testKafkaProducer.send(record).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
