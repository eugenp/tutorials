package com.baeldung.port.input;

import com.baeldung.domain.Reminder;

public interface ReminderServicePort {

    Reminder createReminder(String message, String reminderTime);

    Reminder getReminder(String reminderId);
}
