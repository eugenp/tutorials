package com.baeldung.spring.kafka.groupId;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = Main.class)
@ActiveProfiles("groupId")
@ComponentScan(basePackages = "com.baeldung.spring.kafka.groupId")
@DirtiesContext
@EmbeddedKafka(partitions = 4, topics = { "${kafka.topic.name:test-topic}" }, brokerProperties = { "listeners=PLAINTEXT://localhost:8000", "port=8000" })
public class MainLiveTest {

    @Autowired
    private MyKafkaConsumer consumer;
    @Autowired
    private MyKafkaProducer producer;

    @BeforeEach
    void setup() {
        consumer.resetLatch();
    }

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived() throws Exception {
        String data = "Test 123...";
        producer.send(data);
        boolean messageConsumed = consumer.getLatch()
            .await(10, TimeUnit.SECONDS);
        assertTrue(messageConsumed);
        assertThat(consumer.getPayload(), containsString(data));
    }
}
