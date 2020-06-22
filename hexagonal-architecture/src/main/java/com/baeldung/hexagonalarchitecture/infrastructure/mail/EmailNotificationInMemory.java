package com.baeldung.hexagonalarchitecture.infrastructure.mail;

import com.baeldung.hexagonalarchitecture.domain.NotificationSender;
import com.baeldung.hexagonalarchitecture.domain.Temperature;

import java.util.Arrays;
import java.util.List;

public class EmailNotificationInMemory implements NotificationSender {

    private List<String> subscribers = Arrays.asList("subscriber-1", "subscriber-2");

    @Override public void send(Temperature temperature) {

        subscribers.forEach(subscriber -> {
            // use temperature.print() to send email
        });

    }
}
