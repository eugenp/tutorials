package com.baeldung.hexogonal.store.emailsender;

import com.baeldung.hexagonal.store.core.context.order.infrastructure.EmailNotificationSender;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class SMTPEmailSender implements EmailNotificationSender {

    public static final String FROM_MAIL = "example@baeldung.com";

    @Override
    public void sendEmailMessage(String targetEmail, String subject, String body) {
        try {
            Session session = createSession();
            MimeMessage message = new MimeMessage(session);
            message.setContent(body, "text/html; charset=utf-8");
            message.setFrom(new InternetAddress(FROM_MAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(targetEmail));
            message.setSubject(subject);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.baeldung.com");
        props.put("mail.smtp.port", "587");
        return Session.getInstance(props);
    }
}
