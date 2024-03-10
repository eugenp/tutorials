package com.baeldung.disablingscheduledtasks.unit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import com.baeldung.disablingscheduledtasks.DelayedNotificationScheduler;
import com.baeldung.disablingscheduledtasks.Notification;
import com.baeldung.disablingscheduledtasks.NotificationRepository;
import com.baeldung.disablingscheduledtasks.NotificationService;

public class DelayedNotificationSchedulerUnitTest {

    private final Clock testClock = Clock.fixed(Instant.parse("2024-03-10T10:15:30.00Z"), ZoneId.systemDefault());

    private final NotificationRepository repository = new NotificationRepository();
    private final NotificationService service = new NotificationService(repository, testClock);
    private final DelayedNotificationScheduler scheduler = new DelayedNotificationScheduler(service);

    @Test
    public void whenTimeIsOverNotificationSendOutTime_thenItShouldBeSent() {
        ZonedDateTime fiveMinutesAgo = ZonedDateTime.now(testClock).minusMinutes(5);
        Notification notification = new Notification(fiveMinutesAgo);
        repository.save(notification);

        scheduler.attemptSendingOutDelayedNotifications();

        Notification processedNotification = repository.findById(notification.getId());
        assertTrue(processedNotification.isSentOut());
    }
}