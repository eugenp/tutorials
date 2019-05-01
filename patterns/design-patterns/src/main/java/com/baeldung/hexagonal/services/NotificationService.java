package com.baeldung.hexagonal.services;

import com.baeldung.hexagonal.domain.Notification;
import com.baeldung.hexagonal.factories.NotificationAdapterFactory;
import com.baeldung.hexagonal.ports.NotificationSenderPort;


public class NotificationService {

    private NotificationAdapterFactory factory = new NotificationAdapterFactory();

    public void dispatchNotification(Notification notification) {
        notification.getTargetUsers().forEach(user -> {
            user.getHandles().forEach(handle -> {
                NotificationSenderPort port = factory.getPort(handle.getType());
                port.notify(handle.getHandle(), notification.getData());
            });
        });
    }
}
