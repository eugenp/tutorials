package com.baeldung.architecture.notifier;

import com.baeldung.architecture.core.ports.ExcessiveEnergyNotifier;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

@Component
public class ExcessiveEnergyEmailNotifier implements ExcessiveEnergyNotifier {

    String fromEmail = "from@gmail.com";
    String toEmail = "to@gmail.com";

    @Override
    public void alertExcessiveEnergyUse(int totalEnergyConsumption) {
        try {
            Message message = composeMessage(totalEnergyConsumption);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Bad error happened here!");
        }
    }

    private Message composeMessage(int totalEnergyConsumption) throws MessagingException {
        Session session = getSession();
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

        message.setSubject("Energy consumption exceeded!");
        String msg = "Your current energy consumption for " + new Date() + " is " + totalEnergyConsumption;

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);
        return message;
    }

    private Session getSession() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.mailtrap.io");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
        return Session.getInstance(prop);
    }
}
