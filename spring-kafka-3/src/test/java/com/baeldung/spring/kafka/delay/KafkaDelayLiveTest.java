package com.baeldung.spring.kafka.delay;

import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Testcontainers
@SpringBootTest(classes = KafkaDelayApplication.class)
@Disabled("This test requires a running docker")
class KafkaDelayLiveTest {

    @Container
    private static KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
    private static KafkaProducer<String, String> testKafkaProducer;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Autowired
    OrderService orderService;

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
    }

    @Test
    void givenKafkaBrokerExists_whenCreateOrderIsReceived_thenMessageShouldBeDelayed() throws Exception {
        // Given
        var orderId = UUID.randomUUID();
        Order order = Order.builder()
            .orderId(orderId)
            .price(1.0)
            .orderGeneratedDateTime(LocalDateTime.now())
            .address(List.of("41 Felix Avenue, Luton"))
            .build();

        String orderString = objectMapper.writeValueAsString(order);
        ProducerRecord<String, String> record = new ProducerRecord<>("web.orders", orderString);

        // When
        testKafkaProducer.send(record)
            .get();
        await().atMost(Duration.ofSeconds(1800))
            .until(() -> {
                // then
                Map<UUID, Order> orders = orderService.getOrders();
                return orders != null && orders.get(orderId) != null && Duration.between(orders.get(orderId)
                        .getOrderGeneratedDateTime(), orders.get(orderId)
                        .getOrderProcessedTime())
                    .getSeconds() >= 10;
            });
    }

    @Test
    void givenKafkaBrokerExists_whenCreateOrderIsReceivedForOtherTopics_thenMessageShouldNotBeDelayed() throws Exception {
        // Given
        var orderId = UUID.randomUUID();
        Order order = Order.builder()
            .orderId(orderId)
            .price(1.0)
            .orderGeneratedDateTime(LocalDateTime.now())
            .address(List.of("41 Felix Avenue, Luton"))
            .build();

        String orderString = objectMapper.writeValueAsString(order);
        ProducerRecord<String, String> record = new ProducerRecord<>("web.internal.orders", orderString);

        // When
        testKafkaProducer.send(record)
            .get();
        await().atMost(Duration.ofSeconds(1800))
            .until(() -> {
                // Then
                Map<UUID, Order> orders = orderService.getOrders();
                System.out.println("Time...." + Duration.between(orders.get(orderId)
                        .getOrderGeneratedDateTime(), orders.get(orderId)
                        .getOrderProcessedTime())
                    .getSeconds());
                return orders != null && orders.get(orderId) != null && Duration.between(orders.get(orderId)
                        .getOrderGeneratedDateTime(), orders.get(orderId)
                        .getOrderProcessedTime())
                    .getSeconds() <= 1;
            });
    }

}