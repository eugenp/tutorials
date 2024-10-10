package com.baeldung.seek;

import static com.baeldung.seek.SeekController.TOPIC_NAME;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class SeekControllerLiveTest {

    @Autowired
    private WebTestClient webClient;

    @Container
    private static KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
    private static KafkaProducer<String, String> testKafkaProducer;

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
        int partition = 0;
        IntStream.range(0, 5)
            .forEach(m -> {
                String key = String.valueOf(new Random().nextInt());
                String value = "Message no : %s".formatted(m);
                ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, partition, key, value);
                try {
                    testKafkaProducer.send(record)
                        .get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    @Test
    void givenKafkaBrokerExists_whenSeekByPartitionAndOffset_thenMessageShouldBeRetrieved() {
        this.webClient.get()
            .uri("/seek/api/v1/partition/0/offset/2")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .isEqualTo("{\"partition\":0,\"offset\":2,\"value\":\"Message no : 2\"}");
    }

    @Test
    void givenKafkaBrokerExists_whenSeekByBeginning_thenFirstMessageShouldBeRetrieved() {
        this.webClient.get()
            .uri("/seek/api/v1/partition/0/beginning")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .isEqualTo("{\"partition\":0,\"offset\":0,\"value\":\"Message no : 0\"}");
    }

    @Test
    void givenKafkaBrokerExists_whenSeekByEnd_thenLatestOffsetShouldBeRetrieved() {
        this.webClient.get()
            .uri("/seek/api/v1/partition/0/end")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Long.class)
            .isEqualTo(5L);
    }

}