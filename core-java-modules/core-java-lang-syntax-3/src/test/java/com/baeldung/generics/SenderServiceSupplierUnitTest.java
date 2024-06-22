package com.baeldung.generics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SenderServiceSupplierUnitTest {

    @Test
    void givenEmailSender_whenCreateInstanceUsingSupplier_thenReturnResult() {
        SenderServiceSupplier<EmailSender> service = new SenderServiceSupplier<>(EmailSender::new);

        Sender emailSender = service.createInstance();
        String result = emailSender.send();

        assertEquals("EMAIL", result);
    }

    @Test
    void givenEmailSenderWithCustomConstructor_whenCreateInstanceUsingSupplier_thenReturnResult() {
        SenderServiceSupplier<EmailSender> service = new SenderServiceSupplier<>(() -> new EmailSender("Baeldung"));

        Sender emailSender = service.createInstance();
        String result = emailSender.send();

        assertEquals("EMAIL", result);
    }

    @Test
    void givenNotificationSender_whenCreateInstanceUsingSupplier_thenReturnCorrectResult() {
        SenderServiceSupplier<NotificationSender<String>> service = new SenderServiceSupplier<>(NotificationSender::new);

        Sender notificationSender = service.createInstance();
        String result = notificationSender.send();

        assertEquals("NOTIFICATION", result);
    }
}
