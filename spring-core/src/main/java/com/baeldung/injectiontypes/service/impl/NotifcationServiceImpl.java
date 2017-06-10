package com.baeldung.injectiontypes.service.impl;

import com.baeldung.injectiontypes.service.NotificationService;

public class NotifcationServiceImpl implements NotificationService {

    @Override
    public String sendNotification(String notificationType, String receiverId) {
        return "Notification type: " + notificationType + ", receiverId: " + receiverId;
    }
}
