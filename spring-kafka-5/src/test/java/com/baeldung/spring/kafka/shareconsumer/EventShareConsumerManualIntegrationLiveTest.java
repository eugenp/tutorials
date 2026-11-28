package com.baeldung.spring.kafka.shareconsumer;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Duration;
import java.util.List;

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
import org.springframework.kafka.support.ShareAcknowledgment;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.baeldung.spring.kafka.shareconsumer.config.EventProducerConfig;
import com.baeldung.spring.kafka.shareconsumer.config.ShareConsumerConfig;
import com.baeldung.spring.kafka.shareconsumer.model.Event;

@SpringBootTest(classes = { ShareConsumerConfig.class, EventProducerConfig.class })
@Import({ TestcontainersConfiguration.class, EventShareConsumerManualIntegrationLiveTest.Config.class })
public class EventShareConsumerManualIntegrationLiveTest {

    private static final String TOPIC = "stopic";

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    @MockitoSpyBean
    private ManualAckEventShareConsumer manualAckEventShareConsumer;

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

        await().during(Duration.ofSeconds(5))
            .atMost(Duration.ofSeconds(15))
            .pollInterval(Duration.ofSeconds(1))
            .untilAsserted(() -> verify(manualAckEventShareConsumer, times(1)).consume(argThat((ConsumerRecord<String, Event> record) -> record.value()
                .id() == 1L), any(ShareAcknowledgment.class)));
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
