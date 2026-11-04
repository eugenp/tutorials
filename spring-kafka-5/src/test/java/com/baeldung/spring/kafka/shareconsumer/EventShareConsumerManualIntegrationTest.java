package com.baeldung.spring.kafka.shareconsumer;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import jakarta.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.ShareKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.ShareAcknowledgment;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.baeldung.spring.kafka.shareconsumer.config.EventProducerConfig;
import com.baeldung.spring.kafka.shareconsumer.config.ShareConsumerConfig;
import com.baeldung.spring.kafka.shareconsumer.model.Event;

@SpringBootTest(classes = { ShareConsumerConfig.class, EventProducerConfig.class })
@Import({ TestcontainersConfiguration.class, EventShareConsumerManualIntegrationTest.Config.class })
public class EventShareConsumerManualIntegrationTest {

    private static final String TOPIC = "stopic";

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    @MockitoSpyBean
    private ManualAckEventShareConsumer manualAckEventShareConsumer;

    @BeforeEach
    void ensureShareGroupIsDelivering() {
        clearInvocations(manualAckEventShareConsumer);
        String probeKey = UUID.randomUUID()
            .toString();
        await().atMost(Duration.ofSeconds(35))
            .pollInterval(Duration.ofSeconds(3))
            .untilAsserted(() -> {
                kafkaTemplate.send(TOPIC, probeKey, new Event(-1L, "warmup"));
                verify(manualAckEventShareConsumer).consume(argThat((ConsumerRecord<String, Event> record) -> probeKey.equals(record.key())),
                    any(ShareAcknowledgment.class));
            });
        clearInvocations(manualAckEventShareConsumer);
    }

    @Configuration
    public static class Config {

        @Autowired
        ShareKafkaListenerContainerFactory<String, Event> shareKafkaListenerContainerFactory;

        @Bean
        public ManualAckEventShareConsumer manualAckEventShareConsumer() {
            return new ManualAckEventShareConsumer();
        }

        @PostConstruct
        public void manualErrorHandlingCustomizer() {
            shareKafkaListenerContainerFactory.getContainerProperties()
                .setShareAckMode(ContainerProperties.ShareAckMode.MANUAL);
        }

    }

    @Test
    void givenSingleEvent_whenNonTransientException_thenItIsNotRetried() {
        kafkaTemplate.send(TOPIC, "1", new Event(1L, "first"));

        await().atMost(Duration.ofSeconds(10))
            .pollInterval(Duration.ofMillis(1000))
            .untilAsserted(() -> verify(manualAckEventShareConsumer, times(1)).consume(argThat((ConsumerRecord<String, Event> record) -> List.of(1L)
                .contains(record.value()
                    .id())), any(ShareAcknowledgment.class)));
    }

    @Test
    void givenSingleEvent_whenTransientException_thenItIsRetried() {
        kafkaTemplate.send(TOPIC, "3", new Event(3L, "first"));

        await().atMost(Duration.ofSeconds(10))
            .pollInterval(Duration.ofMillis(1000))
            .untilAsserted(() -> verify(manualAckEventShareConsumer, atLeast(2)).consume(argThat((ConsumerRecord<String, Event> record) -> List.of(3L)
                .contains(record.value()
                    .id())), any(ShareAcknowledgment.class)));
    }

}
