package com.baeldung.spring.kafka.shareconsumer;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.baeldung.spring.kafka.shareconsumer.config.EventProducerConfig;
import com.baeldung.spring.kafka.shareconsumer.config.ShareConsumerConfig;
import com.baeldung.spring.kafka.shareconsumer.consumer.EventShareConsumer;
import com.baeldung.spring.kafka.shareconsumer.model.Event;

@SpringBootTest(classes = { ShareConsumerConfig.class, EventProducerConfig.class })
@Import({ TestcontainersConfiguration.class, DualConsumersConfiguration.class })
class EventShareConsumerIntegrationTest {

    private static final String TOPIC = "stopic";

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    @MockitoSpyBean("eventShareConsumer1")
    private EventShareConsumer eventShareConsumer1;

    @MockitoSpyBean("eventShareConsumer2")
    private EventShareConsumer eventShareConsumer2;

    @BeforeEach
    void ensureShareGroupIsDelivering() {
        clearInvocations(eventShareConsumer1);
        clearInvocations(eventShareConsumer2);
        String probeKey = UUID.randomUUID()
            .toString();
        await().atMost(Duration.ofSeconds(15))
            .pollInterval(Duration.ofSeconds(3))
            .untilAsserted(() -> {
                kafkaTemplate.send(TOPIC, probeKey, new Event(-1L, "warmup"));
                verify(eventShareConsumer1, atLeast(1)).consume(argThat((ConsumerRecord<String, Event> record) -> probeKey.equals(record.key())));
            });
        await().atMost(Duration.ofSeconds(15))
            .pollInterval(Duration.ofSeconds(3))
            .untilAsserted(() -> {
                kafkaTemplate.send(TOPIC, probeKey, new Event(-1L, "warmup"));
                verify(eventShareConsumer2, atLeast(1)).consume(argThat((ConsumerRecord<String, Event> record) -> probeKey.equals(record.key())));
            });
        clearInvocations(eventShareConsumer1);
        clearInvocations(eventShareConsumer2);
    }

    @Test
    void givenMultipleEvents_whenSent_thenAllConsumersConsume() {
        kafkaTemplate.send(TOPIC, "1", new Event(1L, "first"));
        kafkaTemplate.send(TOPIC, "2", new Event(2L, "second"));

        await().atMost(Duration.ofSeconds(7L))
            .pollInterval(Duration.ofSeconds(1L))
            .untilAsserted(() -> verify(eventShareConsumer1, times(1)).consume(argThat((ConsumerRecord<String, Event> record) -> List.of(1L, 2L)
                .contains(record.value()
                    .id()))));

        await().atMost(Duration.ofSeconds(7L))
            .pollInterval(Duration.ofSeconds(1L))
            .untilAsserted(() -> verify(eventShareConsumer2, times(1)).consume(argThat((ConsumerRecord<String, Event> record) -> List.of(1L, 2L)
                .contains(record.value()
                    .id()))));
    }

}