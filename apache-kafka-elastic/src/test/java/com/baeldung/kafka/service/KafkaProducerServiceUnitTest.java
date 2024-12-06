package com.baeldung.kafka.service;

import com.baeldung.kafka.configs.KafkaTopicConfig;
import com.baeldung.kafka.model.NotificationModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

class KafkaProducerServiceUnitTest {

    @Mock
    private KafkaTemplate<String, NotificationModel> kafkaTemplate;

    @InjectMocks
    private KafkaProducerService kafkaProducerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenApiCallThenCheckSend() {
        NotificationModel notificationModel = new NotificationModel(1, "Test message", 2);

        kafkaProducerService.sendMessage(notificationModel);

        // Verify that the message is sent to the correct Kafka topic
        verify(kafkaTemplate, times(1)).send(KafkaTopicConfig.TOPIC_NAME, notificationModel);
    }
}
