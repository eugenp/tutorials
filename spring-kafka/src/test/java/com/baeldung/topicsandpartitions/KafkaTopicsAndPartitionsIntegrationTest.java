package com.baeldung.topicsandpartitions;

import com.baeldung.topicsandpartitions.TemperatureConsumer;
import com.baeldung.topicsandpartitions.ThermostatApplicationKafkaApp;
import com.baeldung.topicsandpartitions.ThermostatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ThermostatApplicationKafkaApp.class)
@EmbeddedKafka(partitions = 2, controlledShutdown = true, brokerProperties = {"listeners=PLAINTEXT://localhost:9094", "port=9094"}, topics = {"multitype"})
@ActiveProfiles("topicsandpartitions")
public class KafkaTopicsAndPartitionsIntegrationTest {
    @Autowired
    private ThermostatService service;

    @Autowired
    private TemperatureConsumer consumer;

    @Test
    public void givenTopic_andConsumerGroup_whenConsumersListenToEvents_thenConsumeItCorrectly() throws Exception {
        service.measureCelsiusAndPublish(10000);
        Thread.sleep(1000);
        System.out.println(consumer.consumedRecords);
    }
}
