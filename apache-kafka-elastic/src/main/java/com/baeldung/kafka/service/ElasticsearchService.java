package com.baeldung.kafka.service;

import com.baeldung.kafka.entity.NotificationEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
public class ElasticsearchService {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public NotificationEntity saveData(NotificationEntity notificationEntity) {
        return elasticsearchOperations.save(notificationEntity);
    }
}
