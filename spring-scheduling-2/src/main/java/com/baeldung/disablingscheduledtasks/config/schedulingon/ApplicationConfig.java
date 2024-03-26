package com.baeldung.disablingscheduledtasks.config.schedulingon;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.baeldung.disablingscheduledtasks.DelayedNotificationScheduler;
import com.baeldung.disablingscheduledtasks.NotificationRepository;
import com.baeldung.disablingscheduledtasks.NotificationService;

@Configuration
@EnableScheduling
public class ApplicationConfig {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public NotificationRepository notificationRepository() {
        return new NotificationRepository();
    }

    @Bean
    public NotificationService notificationService(NotificationRepository notificationRepository, Clock clock) {
        return new NotificationService(notificationRepository, clock);
    }

    @Bean
    public DelayedNotificationScheduler delayedNotificationScheduler(NotificationService notificationService) {
        return new DelayedNotificationScheduler(notificationService);
    }
}