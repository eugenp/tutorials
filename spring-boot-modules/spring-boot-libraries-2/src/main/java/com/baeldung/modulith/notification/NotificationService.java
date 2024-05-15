package com.baeldung.modulith.notification;

import com.baeldung.modulith.notification.internal.Notification;
import com.baeldung.modulith.notification.internal.NotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.ApplicationModuleListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationService.class);

    public void createNotification(NotificationDTO notificationDTO) {
        Notification notification = toEntity(notificationDTO);
        LOG.info("Received notification by module dependency for product {} in date {} by {}.", notification.getProductName()
          , notification.getDate(), notification.getFormat());
    }

    @Async
    @ApplicationModuleListener
    public void notificationEvent(NotificationDTO event) {
        Notification notification = toEntity(event);
        LOG.info("Received notification by event for product {} in date {} by {}.", notification.getProductName()
          , notification.getDate(), notification.getFormat());
    }

    private Notification toEntity(NotificationDTO notificationDTO) {
        Notification notification = new Notification();
        notification.setDate(notificationDTO.getDate());
        if (notificationDTO.getFormat().equals("SMS")) {
            notification.setFormat(NotificationType.SMS);
        }
        if (notificationDTO.getFormat().equals("EMAIL")) {
            notification.setFormat(NotificationType.EMAIL);
        }
        notification.setProductName(notificationDTO.getProductName());
        return notification;
    }
}