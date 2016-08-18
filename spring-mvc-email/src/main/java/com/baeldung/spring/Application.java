package com.baeldung.spring;

import com.baeldung.spring.mail.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    @Autowired
    public EmailServiceImpl emailService;

    @PostConstruct
    public void postConstruct() {
        sendSimpleEmail();
    }

    private void sendSimpleEmail() {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("from@test.com");
        message.setTo("to@test.com");
        message.setSubject("Test Message");

        emailService.sendMail(message);
    }
}
