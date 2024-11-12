package com.baeldung.kafka.service;

import com.baeldung.kafka.configs.KafkaTopicConfig;
import com.baeldung.kafka.model.NotificationModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, NotificationModel> kafkaTemplate;

    public void sendMessage(NotificationModel notificationModel) {
        log.error("original message reached kafka producer service " + notificationModel.getMessage());
        kafkaTemplate.send(KafkaTopicConfig.TOPIC_NAME, notificationModel);
    }
}
