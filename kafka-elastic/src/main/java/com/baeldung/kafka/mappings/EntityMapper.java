package com.baeldung.kafka.mappings;

import com.baeldung.kafka.entity.NotificationEntity;
import com.baeldung.kafka.model.NotificationModel;

public class EntityMapper {

    public static NotificationEntity mapToEntity(NotificationModel notificationModel) {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setMessage(notificationModel.getMessage());
        return notificationEntity;
    }
}
