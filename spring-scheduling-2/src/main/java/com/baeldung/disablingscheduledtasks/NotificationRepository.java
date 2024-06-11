package com.baeldung.disablingscheduledtasks;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class NotificationRepository {

    private Collection<Notification> notifications = new ConcurrentLinkedQueue<>();

    public Notification findById(UUID notificationId) {
        return notifications.stream()
            .filter(n -> notificationId.equals(n.getId()))
            .findFirst()
            .orElseThrow(NoSuchElementException::new);
    }

    public List<Notification> findAllAwaitingSendOut() {
        return notifications.stream()
            .filter(notification -> !notification.isSentOut())
            .collect(Collectors.toList());
    }

    public void save(Notification notification) {
        notifications.add(notification);
    }
}