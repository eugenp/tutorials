package com.baeldung.spring.kafka.topicsandpartitions;

import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = ThermostatApplicationKafkaApp.class)
@EmbeddedKafka(partitions = 2, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class KafkaTopicsAndPartitionsIntegrationTest {
    @ClassRule
    public static EmbeddedKafkaBroker embeddedKafka = new EmbeddedKafkaBroker(1, true, "multitype");

    @Autowired
    private ThermostatService service;

    @Autowired
    private TemperatureConsumer consumer;

    @Test
    public void givenTopic_andConsumerGroup_whenConsumersListenToEvents_thenConsumeItCorrectly() throws Exception {
        service.measureCelsiusAndPublish(10000);
        consumer.getLatch().await(1, TimeUnit.SECONDS);
        System.out.println(consumer.consumedRecords);
    }
}
