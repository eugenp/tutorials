package com.baeldung.reactorbus.service;

import com.baeldung.reactorbus.domain.NotificationData;

public interface NotificationService {

    void initiateNotification(NotificationData notificationData) throws InterruptedException;

}
