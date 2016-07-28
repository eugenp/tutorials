package com.baeldung.spring.mail;

import org.springframework.mail.SimpleMailMessage;

/**
 * Created by Olga on 7/15/2016.
 */
public class Application {

    public static void main(String ...args) {
        MailService mailService = new MailService();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("from@test.com");
        message.setTo("to@test.com");
        message.setSubject("Test Message");

        mailService.sendMail(message);
    }
}
