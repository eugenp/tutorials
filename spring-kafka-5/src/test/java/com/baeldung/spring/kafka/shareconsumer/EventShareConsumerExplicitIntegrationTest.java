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

import jakarta.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.ShareKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.ShareConsumerRecordRecoverer;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.baeldung.spring.kafka.shareconsumer.config.EventProducerConfig;
import com.baeldung.spring.kafka.shareconsumer.config.ShareConsumerConfig;
import com.baeldung.spring.kafka.shareconsumer.model.Event;

public class EventShareConsumerExplicitIntegrationTest {

    private static final String TOPIC = "stopic";

    @Configuration
    public static class Config {

        @Bean
        public ThrowingEventShareConsumer throwingEventShareConsumer() {
            return new ThrowingEventShareConsumer();
        }

    }

    @Nested
    @SpringBootTest(classes = { ShareConsumerConfig.class, EventProducerConfig.class })
    @Import({ TestcontainersConfiguration.class, EventShareConsumerExplicitIntegrationTest.Config.class,
        EventShareConsumerExplicitIntegrationTest.Rejecting.RejectingConfig.class })
    class Rejecting {

        @Autowired
        private KafkaTemplate<String, Event> kafkaTemplate;

        @MockitoSpyBean
        private ThrowingEventShareConsumer throwingEventShareConsumer;

        @BeforeEach
        void ensureShareGroupIsDelivering() {
            clearInvocations(throwingEventShareConsumer);
            String probeKey = UUID.randomUUID()
                .toString();
            await().atMost(Duration.ofSeconds(35))
                .pollInterval(Duration.ofSeconds(3))
                .untilAsserted(() -> {
                    kafkaTemplate.send(TOPIC, probeKey, new Event(-1L, "warmup"));
                    verify(throwingEventShareConsumer).consume(argThat((ConsumerRecord<String, Event> record) -> probeKey.equals(record.key())));
                });
            clearInvocations(throwingEventShareConsumer);
        }

        @Configuration
        static class RejectingConfig {

            @Autowired
            ShareKafkaListenerContainerFactory<String, Event> shareKafkaListenerContainerFactory;

            @PostConstruct
            public void implicitErrorHandlingCustomizer() {
                shareKafkaListenerContainerFactory.getContainerProperties()
                    .setShareAckMode(ContainerProperties.ShareAckMode.EXPLICIT);
                shareKafkaListenerContainerFactory.setShareConsumerRecordRecoverer(ShareConsumerRecordRecoverer.REJECTING);
            }
        }

        @Test
        void givenSingleEvent_whenSent_thenItIsNotRetried() {
            kafkaTemplate.send(TOPIC, "1", new Event(1L, "first"));

            await().atMost(Duration.ofSeconds(10))
                .pollInterval(Duration.ofMillis(1000))
                .untilAsserted(() -> verify(throwingEventShareConsumer, times(1)).consume(argThat((ConsumerRecord<String, Event> record) -> List.of(1L)
                    .contains(record.value()
                        .id()))));
        }

    }

    @Nested
    @SpringBootTest(classes = { ShareConsumerConfig.class, EventProducerConfig.class })
    @Import({ TestcontainersConfiguration.class, EventShareConsumerExplicitIntegrationTest.Config.class,
        EventShareConsumerExplicitIntegrationTest.Releasing.ReleasingConfig.class })
    class Releasing {

        @Autowired
        private KafkaTemplate<String, Event> kafkaTemplate;

        @MockitoSpyBean
        private ThrowingEventShareConsumer throwingEventShareConsumer;

        @BeforeEach
        void ensureShareGroupIsDelivering() {
            clearInvocations(throwingEventShareConsumer);
            String probeKey = UUID.randomUUID()
                .toString();
            await().atMost(Duration.ofSeconds(35))
                .pollInterval(Duration.ofSeconds(3))
                .untilAsserted(() -> {
                    kafkaTemplate.send(TOPIC, probeKey, new Event(-1L, "warmup"));
                    verify(throwingEventShareConsumer, atLeast(1)).consume(argThat((ConsumerRecord<String, Event> record) -> probeKey.equals(record.key())));
                });
            clearInvocations(throwingEventShareConsumer);
        }

        @Configuration
        static class ReleasingConfig {

            @Autowired
            ShareKafkaListenerContainerFactory<String, Event> shareKafkaListenerContainerFactory;

            @PostConstruct
            public void implicitErrorHandlingCustomizer() {
                shareKafkaListenerContainerFactory.getContainerProperties()
                    .setShareAckMode(ContainerProperties.ShareAckMode.EXPLICIT);
                shareKafkaListenerContainerFactory.setShareConsumerRecordRecoverer(ShareConsumerRecordRecoverer.RELEASING);
            }
        }

        @Test
        void givenSingleEvent_whenSent_thenItIsRetried() {
            kafkaTemplate.send(TOPIC, UUID.randomUUID()
                .toString(), new Event(1L, "first"));

            await().atMost(Duration.ofSeconds(10))
                .pollInterval(Duration.ofMillis(1000))
                .untilAsserted(() -> verify(throwingEventShareConsumer, atLeast(2)).consume(argThat((ConsumerRecord<String, Event> record) -> List.of(1L)
                    .contains(record.value()
                        .id()))));
        }

    }

}
