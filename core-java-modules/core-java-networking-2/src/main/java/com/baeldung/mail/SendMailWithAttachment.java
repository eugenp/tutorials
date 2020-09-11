package com.baeldung.mail;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMailWithAttachment {
    public static void main(String[] args) {

        String to = "mail@gmail.com";
        String from = "mail@gmail.com";
        final String username = "abcd";// change accordingly
        final String password = "abcd";// change accordingly
        String host = "smtp.mailtrap.io";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "2525");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Testing Subject");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("This is message body");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            MimeBodyPart attachmentPart2 = new MimeBodyPart();

            attachmentPart.attachFile(new File("C:\\Document1.txt"));
            attachmentPart2.attachFile(new File("C:\\Document2.txt"));

            multipart.addBodyPart(attachmentPart);
            multipart.addBodyPart(attachmentPart2);

            message.setContent(multipart);

            Transport.send(message);

        } catch (Exception e) {
            //Handle the Exception
        }
    }
}