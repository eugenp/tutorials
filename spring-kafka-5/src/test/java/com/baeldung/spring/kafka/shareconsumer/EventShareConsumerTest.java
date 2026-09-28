package com.baeldung.spring.kafka.shareconsumer;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.baeldung.spring.kafka.shareconsumer.config.EventProducerConfig;
import com.baeldung.spring.kafka.shareconsumer.config.ShareConsumerConfig;
import com.baeldung.spring.kafka.shareconsumer.consumer.EventShareConsumer;
import com.baeldung.spring.kafka.shareconsumer.model.Event;

@SpringBootTest(classes = { ShareConsumerConfig.class, EventShareConsumer.class, EventProducerConfig.class })
@Import(TestcontainersConfiguration.class)
class EventShareConsumerTest {

    private static final String TOPIC = "stopic";

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    @MockitoSpyBean
    private EventShareConsumer eventShareConsumer;

    @BeforeEach
    void ensureShareGroupIsDelivering() {
        Mockito.clearInvocations(eventShareConsumer);
        String probeKey = UUID.randomUUID()
            .toString();
        await().atMost(Duration.ofSeconds(120))
            .pollInterval(Duration.ofSeconds(10))
            .untilAsserted(() -> {
                kafkaTemplate.send(TOPIC, probeKey, new Event(-1L, "warmup"));
                verify(eventShareConsumer).consume(argThat((ConsumerRecord<String, Event> record) -> probeKey.equals(record.key())));
            });
        Mockito.clearInvocations(eventShareConsumer);
    }

    @Test
    void shouldConsumeSingleEvent() throws InterruptedException {
        Event expected = new Event(1L, "test-event");
        String key = UUID.randomUUID()
            .toString();

        kafkaTemplate.send(TOPIC, key, expected);

        await().atMost(Duration.ofSeconds(10))
            .pollInterval(Duration.ofMillis(500))
            .untilAsserted(() -> {
                verify(eventShareConsumer).consume(argThat((ConsumerRecord<String, Event> record) -> key.equals(record.key())));
            });

        Thread.sleep(30000L);
    }

    @Test
    void shouldConsumeMultipleEvents() {
        kafkaTemplate.send(TOPIC, new Event(1L, "first"));
        kafkaTemplate.send(TOPIC, new Event(2L, "second"));
        kafkaTemplate.send(TOPIC, new Event(3L, "third"));

        await().atMost(Duration.ofSeconds(20))
            .pollInterval(Duration.ofMillis(1000))
            .untilAsserted(() -> verify(eventShareConsumer, times(3)).consume(argThat((ConsumerRecord<String, Event> record) -> List.of(1L, 2L, 3L)
                .contains(record.value()
                    .id()))));
    }

    @Test
    void shouldProcessEventWithCorrectPayload() {
        Event expected = new Event(42L, "manolis");
        String key = UUID.randomUUID()
            .toString();

        kafkaTemplate.send(TOPIC, key, expected);

        await().atMost(Duration.ofSeconds(60))
            .pollInterval(Duration.ofMillis(500))
            .untilAsserted(() -> {
                verify(eventShareConsumer).consume(argThat((ConsumerRecord<String, Event> record) -> key.equals(record.key()) && record.value()
                    .id() == expected.id() && expected.name()
                    .equals(record.value()
                        .name())));
            });
    }

}