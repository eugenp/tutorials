package com.baeldung.disablingscheduledtasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

public class DelayedNotificationScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DelayedNotificationScheduler.class);

    private NotificationService notificationService;

    public DelayedNotificationScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Scheduled(fixedDelayString = "${notification.send.out.delay}", initialDelayString = "${notification.send.out.initial.delay}")
    public void attemptSendingOutDelayedNotifications() {
        logger.info("Scheduled notifications send out attempt");
        notificationService.sendOutDelayedNotifications();
    }
}