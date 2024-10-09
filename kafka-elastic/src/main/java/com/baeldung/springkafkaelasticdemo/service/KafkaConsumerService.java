package com.itsnaveenk.springkafkaelasticdemo.service;

import com.itsnaveenk.springkafkaelasticdemo.model.NotificationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.itsnaveenk.springkafkaelasticdemo.mappings.EntityMapper.mapToEntity;

@Service
public class KafkaConsumerService {

    @Autowired
    ElasticsearchService elasticsearchService;

    @KafkaListener(topics = "foodsOrder", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(NotificationModel notificationModel) {
        System.out.println("Received notification: " + notificationModel.getMessage());

        try {
            elasticsearchService.saveData(mapToEntity(notificationModel));
            System.out.println("Data Saved in Elastic");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Data Not Saved in Elastic");
        }
    }
}
