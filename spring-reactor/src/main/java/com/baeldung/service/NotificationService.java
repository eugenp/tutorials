package com.baeldung.service;

import com.baeldung.domain.NotificationData;

public interface NotificationService {

    void initiateNotification(NotificationData notificationData) throws InterruptedException;

}
