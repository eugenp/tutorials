package com.baeldung.kafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.baeldung.kafka.mappings.EntityMapper.mapToEntity;

import com.baeldung.kafka.model.NotificationModel;

@Service
@Slf4j
public class KafkaConsumerService {

    @Autowired
    ElasticsearchService elasticsearchService;

    @KafkaListener(topics = KafkaTopicConfig.TOPIC_NAME, groupId = "${spring.kafka.consumer.group-id}")
    public void listen(NotificationModel notificationModel) {
        log.info("Received notification: " + notificationModel.getMessage());

        try {
            elasticsearchService.saveData(mapToEntity(notificationModel));
            log.info("Data Saved in Elastic");

        } catch (Exception e) {
            log.error("Data Not Saved in Elastic", e);
            throw new RuntimeException("Data Not Saved in Elastic", e);
        }
    }
}
