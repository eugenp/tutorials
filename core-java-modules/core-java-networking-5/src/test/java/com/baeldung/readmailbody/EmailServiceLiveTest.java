package com.baeldung.readmailbody;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Properties;

public class EmailServiceLiveTest {

    static Session session;

    @BeforeAll
    static void setup() {
        // Set up the email configuration
        String to = "testing@gmail.com";
        String from = "testing@gmail.com";
        String password = "password";
        String host = "smtp.gmail.com";
        String port = "587";

        // Set up the email content
        String textBody = "This is a text body";
        String htmlBody = "<h1>This is an HTML body</h1>";

        // Set up the email properties
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Create a new email session
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Create a new email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Test Email");

            // Create a new email body
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(textBody, "utf-8", "plain");

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setText(htmlBody, "utf-8", "html");

            // Create a new email multipart
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(htmlPart);

            // Set the email content
            message.setContent(multipart);

            // Send the email
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void givenHTMLContentEmail_whenReadEmail_thenReturnHTMLContent () throws MessagingException, IOException {
        EmailService es = new EmailService(session);
        es.retrieveEmails();
        assertEquals("This is a text body", es.getPlainContent());
        assertEquals("This is an HTML body", es.getHTMLContent());
    }
}
