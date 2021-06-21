package com.baeldung.ddd.hexagonal.arch.adapters.driven.notification;

import com.baeldung.ddd.hexagonal.arch.core.ports.driven.NotificationService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements NotificationService {
    private JavaMailSender emailMessenger;

    public EmailNotificationService(JavaMailSender emailMessenger) {
        this.emailMessenger = emailMessenger;
    }

    @Override
    public void sendMessage(String to, String message) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom("no-reply@hexogonalarch.com");
        emailMessage.setTo(to);
        emailMessage.setSubject("Account Activation");
        emailMessage.setText(message);
        this.emailMessenger.send(emailMessage);
    }
}
