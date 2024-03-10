package com.baeldung.disablingscheduledtasks.integration.dontcreatescheduledbean;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

import com.baeldung.disablingscheduledtasks.Notification;
import com.baeldung.disablingscheduledtasks.NotificationRepository;
import com.baeldung.disablingscheduledtasks.NotificationService;
import com.baeldung.disablingscheduledtasks.config.dontcreatescheduledbean.ApplicationConfig;
import com.baeldung.disablingscheduledtasks.config.dontcreatescheduledbean.ScheduledTasksConfig;

@SpringBootTest(
    classes = { ApplicationConfig.class, ScheduledTasksConfig.class,SchedulerTestConfiguration.class },
    properties = { "notification.send.out.delay: 10" }
)
@ActiveProfiles("integrationTest")
public class DelayedNotificationSchedulerIntegrationTest {

    @Autowired
    private Clock testClock;

    @Autowired
    private NotificationRepository repository;

    @Autowired
    private NotificationService service;

    @Test
    public void whenTimeIsOverNotificationSendOutTime_thenItShouldBeSent() {
        ZonedDateTime fiveMinutesAgo = ZonedDateTime.now(testClock).minusMinutes(5);
        Notification notification = new Notification(fiveMinutesAgo);
        repository.save(notification);

        service.sendOutDelayedNotifications();

        Notification processedNotification = repository.findById(notification.getId());
        assertTrue(processedNotification.isSentOut());
    }
}

@TestConfiguration
class SchedulerTestConfiguration {

    @Bean
    @Primary
    public Clock testClock() {
        return Clock.fixed(Instant.parse("2024-03-10T10:15:30.00Z"), ZoneId.systemDefault());
    }
}