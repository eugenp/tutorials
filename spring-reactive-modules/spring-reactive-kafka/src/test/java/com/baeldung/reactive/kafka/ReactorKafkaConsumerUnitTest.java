package com.baeldung.reactive.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import com.baeldung.config.KafkaConfig;
import com.baeldung.consumer.ConsumerService;
import com.baeldung.publisher.PublisherService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(classes = { KafkaConfig.class, PublisherService.class, ConsumerService.class })
@EmbeddedKafka(partitions = 1, topics = { "test-topic" }, controlledShutdown = true)
@TestPropertySource(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@Slf4j
public class ReactorKafkaConsumerUnitTest {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private PublisherService publisherService;

    private KafkaAdmin kafkaAdmin;

    @BeforeEach
    void setUp() {
        Map<String, Object> map = new HashMap<>();
        map.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        kafkaAdmin = new KafkaAdmin(map);
    }

    @AfterEach
    public void cleanUp() throws ExecutionException, InterruptedException {
        AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());

        DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(Collections.singletonList("test-topic"));
        deleteTopicsResult.all()
            .get();
    }

    @Test
    void whenMessageIsPublished_thenConsumerReceivesSuccessfully() {

        Mono<Void> publisher = publisherService.publish("message", "test-topic");

        Flux<String> consumer = consumerService.consumeRecord();

        StepVerifier.create(publisher)
            .expectComplete()
            .verify();

        StepVerifier.create(consumer)
            .expectNext("message")
            .thenCancel()
            .verify();
    }

    @Test
    void whenMessagesArePublished_thenConsumedAsABatch() {

        Mono<Void> publisher = Mono.when(publisherService.publish("message-1", "test-topic")
            .then(), publisherService.publish("message-2", "test-topic")
            .then());

        Flux<String> consumer = consumerService.consumeAsABatch();

        StepVerifier.create(publisher)
            .expectComplete()
            .verify();

        StepVerifier.create(consumer)
            .expectNext("message-1")
            .expectNext("message-2")
            .thenCancel()
            .verify();
    }

    @Test
    void whenMessagesArePublished_thenConsumedWithLimit() {

        Mono<Void> publisher = Mono.when(publisherService.publish("message-1", "test-topic")
            .then(), publisherService.publish("message-2", "test-topic")
            .then());

        Flux<String> consumer = consumerService.consumeWithLimit();

        StepVerifier.create(publisher)
            .expectComplete()
            .verify();

        StepVerifier.create(consumer)
            .expectNextCount(2)
            .thenCancel()
            .verify();
    }

    @Test
    void whenMessageIsPublished_thenConsumedWithRetry() {

        AtomicInteger attempts = new AtomicInteger();
        Mono<Void> publisher = publisherService.publish("message", "test-topic");

        Flux<String> consumer = consumerService.consumeWithRetryWithBackOff(attempts);

        StepVerifier.create(publisher)
            .expectComplete()
            .verify();

        StepVerifier.create(consumer)
            .expectNext("message")
            .thenCancel()
            .verify();

        assertEquals(3, attempts.get());
    }
}
