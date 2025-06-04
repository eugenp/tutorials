package com.baeldung.email.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.email.service.EmailService;

@SpringBootApplication(scanBasePackages = { "com.baeldung.email.service" })
public class EmailSenderApplication implements CommandLineRunner {

    private final EmailService emailService;

    public EmailSenderApplication(EmailService emailService) {
        this.emailService = emailService;
    }

    public static void main(String[] args) {
        SpringApplication.run(EmailSenderApplication.class, args);
    }

    @Override
    public void run(String... args) {
        emailService.sendSimpleEmail("recipient@baeldung.com", "Test Subject", "Testing the Spring Boot Email!");
    }
}
