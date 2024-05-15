package com.baeldung.conditionalonproperty.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.conditionalonproperty.service.EmailNotification;
import com.baeldung.conditionalonproperty.service.NotificationSender;
import com.baeldung.conditionalonproperty.service.SmsNotification;

@Configuration
public class NotificationConfig {

    @Bean(name = "emailNotification")
    @ConditionalOnProperty(prefix = "notification", name = "service", havingValue = "email")
    public NotificationSender notificationSender() {
        return new EmailNotification();
    }

    @Bean(name = "smsNotification")
    @ConditionalOnProperty(prefix = "notification", name = "service", havingValue = "sms")
    public NotificationSender notificationSender2() {
        return new SmsNotification();
    }

}
