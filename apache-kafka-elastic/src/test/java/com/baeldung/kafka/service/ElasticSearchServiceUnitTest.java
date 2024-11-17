package com.baeldung.kafka.service;

import com.baeldung.kafka.entity.NotificationEntity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElasticSearchServiceUnitTest {

    @Mock
    private ElasticsearchOperations elasticsearchOperations;

    @InjectMocks
    private ElasticsearchService elasticsearchService;

    @Test
    void givenTestDataThenCheckSave() {
        NotificationEntity notificationEntity = new NotificationEntity(1, "Test message", 2);

        when(elasticsearchOperations.save(notificationEntity)).thenReturn(notificationEntity);

        NotificationEntity savedEntity = elasticsearchService.saveData(notificationEntity);

        // Verify the save method was called with the correct entity
        verify(elasticsearchOperations, times(1)).save(notificationEntity);
        assert savedEntity.equals(notificationEntity);
    }
}
