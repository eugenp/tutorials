package com.baeldung.injectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.injectiontypes.service.NotificationService;

@Component
public class SpringRunner {
    private NotificationService notificationService;

    @Autowired
    public SpringRunner(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public SpringRunner() {
        super();
    }

}