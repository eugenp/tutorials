package com.baeldung.spring.cloud.aws.sns;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class SNSMessageSender {

    @Autowired
    NotificationMessagingTemplate notificationMessagingTemplate;

    public void send(String topicName, Object message, String subject) {
        notificationMessagingTemplate.sendNotification(topicName, message, subject);
    }
}
