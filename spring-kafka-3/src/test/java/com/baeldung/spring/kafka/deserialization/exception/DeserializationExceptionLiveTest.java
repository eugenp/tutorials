package com.baeldung.spring.kafka.deserialization.exception;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(classes = Application.class)
class DeserializationExceptionLiveTest {

    @Container
    private static KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    private static KafkaProducer<String, String> testKafkaProducer;

    @MockBean
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


    @Test
    void test() throws Exception {

        LocalDate date = LocalDate.of(2021, 1, 1);
        String dateStr = "2021-01-01";

        publishArticle("""
            { 
                "article": "Introduction to Kafka",
                "author": "John Doe",
            }
            """);

        await().untilAsserted(() ->
            verify(emailService).sendNewsletter(
                new Article("Introduction to Java", "John Doe")));
    }


    @Test
    void test2() throws Exception {
        publishArticle("""
            { 
                "Invalid JSON",
            }
            """);

        await().untilAsserted(() ->
            verify(emailService).sendNewsletter(
                new Article("Introduction to Kafka", "John Doe")));
    }

    static void publishArticle(String body) throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> record = new ProducerRecord<>("baeldung.articles.published", body);
        testKafkaProducer.send(record).get();
    }
}
