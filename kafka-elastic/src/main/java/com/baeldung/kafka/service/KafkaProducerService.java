package com.baeldung.kafka.service;

import com.baeldung.kafka.model.NotificationModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, NotificationModel> kafkaTemplate;
    public void sendMessage(NotificationModel notificationModel) {
        log.error("original message reached kafka producer service " + notificationModel.getMessage());

        kafkaTemplate.send(KafkaTopicConfig.TOPIC_NAME, notificationModel);
    }
}
