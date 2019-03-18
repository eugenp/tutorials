package com.baeldung.hexagonal.store.infrastructure.emailsender;

import com.baeldung.hexagonal.store.core.context.order.infrastructure.EmailNotificationSender;
import org.springframework.stereotype.Component;

@Component
public class SMTPEmailSender implements EmailNotificationSender {
    @Override
    public void sendEmailMessage(String targetEmail, String subject, String body) {
        // Send message
    }
}
