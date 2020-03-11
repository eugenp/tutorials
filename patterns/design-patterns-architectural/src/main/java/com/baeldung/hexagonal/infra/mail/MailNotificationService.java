package com.baeldung.hexagonal.infra.mail;

import com.baeldung.hexagonal.domain.Customer;
import com.baeldung.hexagonal.domain.NotificationService;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailNotificationService implements NotificationService {

    @Override
    public void notifyCustomerChanged(Customer customer) {
        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.auth", true);
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.mailtrap.io");
            prop.put("mail.smtp.port", "25");
            prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

            Session session = Session.getInstance(prop);

            Message mail = new MimeMessage(session);
            mail.setFrom(new InternetAddress("from@gmail.com"));
            mail.setRecipients(Message.RecipientType.TO, InternetAddress.parse("to@gmail.com"));
            mail.setSubject(String.format("Customer with id %s changed his name to %s", customer.getId(), customer.getName()));

            Transport.send(mail);
        } catch (MessagingException e) {
            throw new IllegalStateException(e);
        }
    }

}
