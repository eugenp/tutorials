package com.baeldung.disablingscheduledtasks.config.dontcreatescheduledbean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.baeldung.disablingscheduledtasks.DelayedNotificationScheduler;
import com.baeldung.disablingscheduledtasks.NotificationService;

@Configuration
@Profile("!integrationTest")
public class ScheduledTasksConfig {

    @Bean
    public DelayedNotificationScheduler delayedNotificationScheduler(NotificationService notificationService) {
        return new DelayedNotificationScheduler(notificationService);
    }
}