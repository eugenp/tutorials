package com.baeldung.seek;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConsumerListenerSeekLiveTest {

    protected static ListAppender<ILoggingEvent> listAppender;

    @Autowired
    ConsumerListener consumerListener;

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
        IntStream.range(0, 5)
            .forEach(m -> {
                ProducerRecord<String, String> record = new ProducerRecord<>("test-seek-topic", 0, String.valueOf(m), "Message no : %s".formatted(m));
                try {
                    testKafkaProducer.send(record)
                        .get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
        testKafkaProducer.flush();
    }

    @Test
    void givenKafkaBrokerExists_whenMessagesAreSent_thenLastMessageShouldBeRetrieved() {
        Map<String, String> messages = consumerListener.MESSAGES;
        Assertions.assertEquals(1, messages.size());
        Assertions.assertEquals("Message no : 4", messages.get("4"));
    }

}