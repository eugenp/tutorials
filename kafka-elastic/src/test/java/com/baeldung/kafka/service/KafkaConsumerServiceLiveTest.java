package com.baeldung.kafka.service;

import com.baeldung.kafka.entity.NotificationEntity;
import com.baeldung.kafka.model.NotificationModel;
import org.junit.BeforeEach;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class KafkaConsumerServiceLiveTest {

    @Mock
    private ElasticsearchService elasticsearchService;

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListen() {
        NotificationModel notificationModel = new NotificationModel(1, "Test message", 2);
        NotificationEntity notificationEntity = new NotificationEntity(1, "Test message", 2);

        when(elasticsearchService.saveData(any(NotificationEntity.class))).thenReturn(notificationEntity);

        kafkaConsumerService.listen(notificationModel);

        // Verify that the message is processed and saved to Elasticsearch
        verify(elasticsearchService, times(1)).saveData(any(NotificationEntity.class));
    }
}
