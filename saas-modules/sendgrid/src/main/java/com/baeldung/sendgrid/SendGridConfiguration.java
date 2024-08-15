package com.baeldung.sendgrid;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.objects.Email;

@Configuration
@EnableConfigurationProperties(SendGridConfigurationProperties.class)
public class SendGridConfiguration {

    private final SendGridConfigurationProperties sendGridConfigurationProperties;

    public SendGridConfiguration(SendGridConfigurationProperties sendGridConfigurationProperties) {
        this.sendGridConfigurationProperties = sendGridConfigurationProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public SendGrid sendGrid() {
        String apiKey = sendGridConfigurationProperties.getApiKey();
        return new SendGrid(apiKey);
    }

    @Bean
    public Email fromEmail() {
        String fromEmail = sendGridConfigurationProperties.getFromEmail();
        String fromName = sendGridConfigurationProperties.getFromName();
        return new Email(fromEmail, fromName);
    }

}