package com.baeldung.streams.maxdate;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DateHelper {

    static final Date findMaxDateOf(List<Event> events) {
        if (events == null || events.isEmpty()) {
            return null;
        }
        return events.stream()
            .map(Event::getDate)
            .max(Date::compareTo)
            .get();
    }

    static final Date findMaxDateOfWithComparator(List<Event> events) {
        if (events == null || events.isEmpty()) {
            return null;
        }
        return events.stream()
            .map(Event::getDate)
            .max(Comparator.naturalOrder())
            .get();
    }

    static final LocalDate findMaxDateOfLocalEvents(List<LocalEvent> events) {
        if (events == null || events.isEmpty()) {
            return null;
        }
        return events.stream()
            .map(LocalEvent::getDate)
            .max(LocalDate::compareTo)
            .get();
    }

    static final LocalDate findMaxDateOfLocalEventsWithComparator(List<LocalEvent> events) {
        if (events == null || events.isEmpty()) {
            return null;
        }
        return events.stream()
            .map(LocalEvent::getDate)
            .max(Comparator.naturalOrder())
            .get();
    }

}
