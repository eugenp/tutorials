package com.baeldung.architecture.hexagonal.infrastructure.notifications;

public interface Notification {
    void notify(MessageDto message);
}
