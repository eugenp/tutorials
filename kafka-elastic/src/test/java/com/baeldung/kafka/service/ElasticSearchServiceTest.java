package com.baeldung.kafka.service;

import com.baeldung.kafka.entity.NotificationEntity;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

class ElasticSearchServiceTest {

    @Mock
    private ElasticsearchOperations elasticsearchOperations;

    @InjectMocks
    private ElasticsearchService elasticsearchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveData() {
        NotificationEntity notificationEntity = new NotificationEntity(1, "Test message", 2);

        when(elasticsearchOperations.save(notificationEntity)).thenReturn(notificationEntity);

        NotificationEntity savedEntity = elasticsearchService.saveData(notificationEntity);

        // Verify the save method was called with the correct entity
        verify(elasticsearchOperations, times(1)).save(notificationEntity);
        assert savedEntity.equals(notificationEntity);
    }
}
