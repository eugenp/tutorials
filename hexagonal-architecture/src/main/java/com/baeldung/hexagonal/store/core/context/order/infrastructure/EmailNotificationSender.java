package com.baeldung.hexagonal.store.core.context.order.infrastructure;

public interface EmailNotificationSender {
    void sendEmailMessage(String targetEmail, String subject, String body);
}
