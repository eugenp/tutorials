package com.baeldung.beaninjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ComponentScan("com.baeldung.beaninjection")
public class ApplicationConfig {
    @Bean
    public MailSender mailSender() {
        JavaMailSender mailSender = new JavaMailSenderImpl();
        return mailSender;
    }
}
