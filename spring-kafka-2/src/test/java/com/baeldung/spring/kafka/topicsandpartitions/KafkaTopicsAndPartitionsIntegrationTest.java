package com.baeldung.spring.kafka.topicsandpartitions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.AcknowledgingConsumerAwareMessageListener;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

import com.baeldung.spring.kafka.retryable.Greeting;
import com.baeldung.spring.kafka.retryable.RetryableApplicationKafkaApp;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = ThermostatApplicationKafkaApp.class)
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class KafkaTopicsAndPartitionsIntegrationTest {
    @ClassRule
    public static EmbeddedKafkaBroker embeddedKafka = new EmbeddedKafkaBroker(1, true, "multitype");

    @Autowired
    private ThermostatService service;

    @Autowired
    private TemperatureConsumer consumer;

    @Before
    public void setup() {
        System.setProperty("spring.kafka.bootstrap-servers", embeddedKafka.getBrokersAsString());
    }

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingAWellFormedMessage_thenMessageIsConsumed() throws Exception {
        service.measureCelsiusAndPublish(15);
        boolean messageConsumed = consumer.getLatch().await(2, TimeUnit.SECONDS);
    }
}
