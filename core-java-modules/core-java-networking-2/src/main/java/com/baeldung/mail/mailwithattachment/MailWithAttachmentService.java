package com.baeldung.mail.mailwithattachment;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class MailWithAttachmentService {

    private final String username;
    private final String password;
    private final String host;
    private final int port;

    MailWithAttachmentService(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    public Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", this.port);

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void sendMail(Session session) throws MessagingException, IOException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("mail@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("mail@gmail.com"));
        message.setSubject("Testing Subject");

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("This is message body");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.attachFile(getFile("attachment.txt"));
        multipart.addBodyPart(attachmentPart);

        MimeBodyPart attachmentPart2 = new MimeBodyPart();
        attachmentPart2.attachFile(getFile("attachment2.txt"));
        multipart.addBodyPart(attachmentPart2);

        message.setContent(multipart);
        Transport.send(message);
    }

    private File getFile(String filename) {
        try {
            URI uri = this.getClass()
              .getClassLoader()
              .getResource(filename)
              .toURI();
            return new File(uri);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to find file from resources: " + filename);
        }
    }

}
