package com.baeldung.architecture.hexagonal.infrastructure.notifications;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class EmailNotification implements Notification {
    @Override
    public void notify(MessageDto message) {
        log.info("Sending email to: {} with content: {}", message.getUser(), message.getMessage());
    }
}
