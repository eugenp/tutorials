package com.baeldung.hexagonalarchitecture.adapter;

import java.time.LocalDateTime;

import com.baeldung.hexagonalarchitecture.port.INotificationManager;

public class ConsoleNotification implements INotificationManager {
    @Override
    public void notify(String notification) {
        System.out.println(
          LocalDateTime.now().toString() + ": " + notification
        );
    }
}
