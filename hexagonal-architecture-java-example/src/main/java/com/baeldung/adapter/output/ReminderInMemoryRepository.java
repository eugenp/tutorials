package com.baeldung.adapter.output;

import com.baeldung.domain.Reminder;
import com.baeldung.exception.ReminderNotFoundException;
import com.baeldung.port.output.ReminderRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ReminderInMemoryRepository implements ReminderRepositoryPort {

    private static final Map<String, Reminder> REMINDER_STORAGE = new ConcurrentHashMap<>();

    @Override
    public void save(Reminder reminder) {
        REMINDER_STORAGE.put(reminder.getId(), reminder);
    }

    @Override
    public Reminder getReminderById(String reminderId) {
        Reminder reminder = REMINDER_STORAGE.get(reminderId);
        if (reminder == null) {
            throw new ReminderNotFoundException("Reminder Not found");
        }
        return reminder;
    }
}
