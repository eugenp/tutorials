package com.baeldung.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Reminder {

    private final String id;
    private final String message;
    private final LocalDateTime reminderTime;

    public Reminder(String message, LocalDateTime reminderTime) {
        this.id = UUID.randomUUID().toString();
        this.message = message;
        this.reminderTime = reminderTime;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }
}
