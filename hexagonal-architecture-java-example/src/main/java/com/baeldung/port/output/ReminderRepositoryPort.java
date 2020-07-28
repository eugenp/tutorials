package com.baeldung.port.output;

import com.baeldung.domain.Reminder;
import org.springframework.stereotype.Repository;

@Repository
public interface ReminderRepositoryPort {

    void save(Reminder reminder);

    Reminder getReminderById(String reminderId);
}
