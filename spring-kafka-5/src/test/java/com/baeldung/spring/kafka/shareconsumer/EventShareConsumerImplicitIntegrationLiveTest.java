package com.baeldung.spring.kafka.shareconsumer;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Duration;

import jakarta.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.ShareKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.baeldung.spring.kafka.shareconsumer.config.EventProducerConfig;
import com.baeldung.spring.kafka.shareconsumer.config.ShareConsumerConfig;
import com.baeldung.spring.kafka.shareconsumer.model.Event;

@SpringBootTest(classes = { ShareConsumerConfig.class, EventProducerConfig.class })
@Import({ TestcontainersConfiguration.class, EventShareConsumerImplicitIntegrationLiveTest.Config.class })
public class EventShareConsumerImplicitIntegrationLiveTest {

    private static final String TOPIC = "stopic";

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    @MockitoSpyBean
    private ThrowingEventShareConsumer throwingEventShareConsumer;

    @Configuration
    public static class Config {

        @Autowired
        ShareKafkaListenerContainerFactory<String, Event> shareKafkaListenerContainerFactory;

        @Bean
        public ThrowingEventShareConsumer throwingEventShareConsumer() {
            return new ThrowingEventShareConsumer();
        }

        @PostConstruct
        public void implicitErrorHandlingCustomizer() {
            shareKafkaListenerContainerFactory.getContainerProperties()
                .setShareAckMode(ContainerProperties.ShareAckMode.IMPLICIT);
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
