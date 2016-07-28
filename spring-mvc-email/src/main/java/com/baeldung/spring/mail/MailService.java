package com.baeldung.spring.mail;

import com.baeldung.spring.web.dto.MailObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created by Olga on 7/15/2016.
 */
@Service
public class MailService {
    @Autowired
    public JavaMailSender mailSender;

    public void sendMail(MimeMessage message) {
        try {
            mailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    public MimeMessage createMessageWithAttachment(MailObject mailObject) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(mailObject.getFrom());
            helper.setTo(mailObject.getTo());
            helper.setSubject(mailObject.getSubject());
            helper.setText(mailObject.getText());

            // attach a sample image attachment
            FileSystemResource file = new FileSystemResource(new File("c:/attachment.jpg"));
            helper.addAttachment("Attachment.jpg", file);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }

    public void sendMail(SimpleMailMessage message) {
        try {
            mailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    @Autowired
    public SimpleMailMessage template;

    public SimpleMailMessage createSimpleMailMessage(MailObject mailObject) {
        SimpleMailMessage mailMessage = new SimpleMailMessage(template);

        mailMessage.setFrom(mailObject.getFrom());
        mailMessage.setTo(mailObject.getTo());
        mailMessage.setSubject(mailObject.getSubject());
        mailMessage.setText(String.format(template.getText(), mailObject.getText()));

        return mailMessage;
    }
}
