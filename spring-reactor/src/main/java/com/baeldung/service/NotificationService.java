package com.baeldung.service;

import com.baeldung.doman.NotificationData;

public interface NotificationService {

    void initiateNotification(NotificationData notificationData) throws InterruptedException;

}
