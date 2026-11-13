package com.baeldung.spring.kafka.shareconsumer;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import jakarta.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecord;
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

public class EventShareConsumerExplicitIntegrationLiveTest {

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
    @Import({ TestcontainersConfiguration.class, EventShareConsumerExplicitIntegrationLiveTest.Config.class,
        EventShareConsumerExplicitIntegrationLiveTest.Rejecting.RejectingConfig.class })
    class Rejecting {

        @Autowired
        private KafkaTemplate<String, Event> kafkaTemplate;

        @MockitoSpyBean
        private ThrowingEventShareConsumer throwingEventShareConsumer;

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

            await().during(Duration.ofSeconds(5))
                .atMost(Duration.ofSeconds(15))
                .pollInterval(Duration.ofSeconds(1))
                .untilAsserted(() -> verify(throwingEventShareConsumer, times(1)).consume(argThat((ConsumerRecord<String, Event> record) -> record.value()
                    .id() == 1L)));
        }

    }

    @Nested
    @SpringBootTest(classes = { ShareConsumerConfig.class, EventProducerConfig.class })
    @Import({ TestcontainersConfiguration.class, EventShareConsumerExplicitIntegrationLiveTest.Config.class,
        EventShareConsumerExplicitIntegrationLiveTest.Releasing.ReleasingConfig.class })
    class Releasing {

        @Autowired
        private KafkaTemplate<String, Event> kafkaTemplate;

        @MockitoSpyBean
        private ThrowingEventShareConsumer throwingEventShareConsumer;

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
