package com.itsnaveenk.springkafkaelasticdemo.service;

import com.itsnaveenk.springkafkaelasticdemo.model.NotificationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class  KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, NotificationModel> kafkaTemplate;

    public void sendMessage(NotificationModel notificationModel) {
        System.out.println("original message reached kafka producer service " + notificationModel.getMessage());

        kafkaTemplate.send("foodsOrder", notificationModel);
    }
}
