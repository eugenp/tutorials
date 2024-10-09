package com.itsnaveenk.springkafkaelasticdemo.mappings;

import com.itsnaveenk.springkafkaelasticdemo.entity.NotificationEntity;
import com.itsnaveenk.springkafkaelasticdemo.model.NotificationModel;

public class EntityMapper {
    public static NotificationEntity mapToEntity(NotificationModel notificationModel) {

        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setMessage(notificationModel.getMessage());
        return notificationEntity;
    }
}
