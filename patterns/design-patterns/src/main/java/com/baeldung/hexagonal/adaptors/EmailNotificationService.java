package com.baeldung.hexagonal.adaptors;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.baeldung.hexagonal.core.CustomMessage;
import com.baeldung.hexagonal.core.Customer;
import com.baeldung.hexagonal.ports.INotification;

public class EmailNotificationService implements INotification {

    public boolean sendNotificatin(Properties properties, Customer customer, CustomMessage customMessages) {
        String to = customer.getEmail();
        String from = properties.getProperty("from");
        String host = properties.getProperty("host");

        // Get the session object
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);

        // compose the message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(customMessages.getSubject());
            message.setText(customMessages.getBody());

            // Send message
            Transport.send(message);
            
            return Boolean.TRUE;

        } catch (MessagingException mex) {
            mex.printStackTrace();
            return Boolean.FALSE;
        }
       
    }

}
