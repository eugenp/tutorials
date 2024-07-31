package com.baeldung.generics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SenderServiceGuavaUnitTest {

    @Test
    void givenEmailSender_whenCreateInstanceUsingGuava_thenReturnResult() {
        SenderServiceGuava<EmailSender> service = new SenderServiceGuava<>(EmailSender.class);

        Sender emailSender = service.createInstance();
        String result = emailSender.send();

        assertEquals("EMAIL", result);
    }

    @Test
    void givenEmailSender_whenCreateInstanceUsingGuavaAndAnonymous_thenReturnResult() {
        SenderServiceGuava<EmailSender> service = new SenderServiceGuava<EmailSender>() {
        };

        Sender emailSender = service.createInstanceAnonymous();
        String result = emailSender.send();

        assertEquals("EMAIL", result);
    }

    @Test
    void givenNotificationSender_whenCreateInstanceUsingGuavaAndAnonymous_thenReturnResult() {
        SenderServiceGuava<NotificationSender<String>> service = new SenderServiceGuava<NotificationSender<String>>() {
        };

        Sender notificationSender = service.createInstanceAnonymous();
        String result = notificationSender.send();

        assertEquals("NOTIFICATION", result);
    }

}