package com.baeldung.disablingscheduledtasks;

import java.time.Clock;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private NotificationRepository notificationRepository;
    private Clock clock;

    public NotificationService(NotificationRepository notificationRepository, Clock clock) {
        this.notificationRepository = notificationRepository;
        this.clock = clock;
    }

    public void sendOutDelayedNotifications() {
        logger.info("Sending out delayed notifications");
        List<Notification> notifications = notificationRepository.findAllAwaitingSendOut();
        notifications.forEach(notification -> notification.sendOut(clock));
    }
}