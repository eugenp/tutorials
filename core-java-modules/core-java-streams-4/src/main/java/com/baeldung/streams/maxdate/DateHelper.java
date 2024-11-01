package com.baeldung.streams.maxdate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    
    static final Date findMinDateOf(List<Event> events) {
        if (events == null || events.isEmpty()) {
            return null;
        }
        return events.stream()
            .map(Event::getDate)
            .min(Date::compareTo)
            .get();
    }

    static final Date findMinDateOfWithComparator(List<Event> events) {
        if (events == null || events.isEmpty()) {
            return null;
        }
        return events.stream()
            .map(Event::getDate)
            .min(Comparator.naturalOrder())
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
    
    static final LocalDate findMinDateOfLocalEvents(List<LocalEvent> events) {
        if (events == null || events.isEmpty()) {
            return null;
        }
        return events.stream()
            .map(LocalEvent::getDate)
            .min(LocalDate::compareTo)
            .get();
    }

    static final LocalDate findMinDateOfLocalEventsWithComparator(List<LocalEvent> events) {
        if (events == null || events.isEmpty()) {
            return null;
        }
        return events.stream()
            .map(LocalEvent::getDate)
            .min(Comparator.naturalOrder())
            .get();
    }

    static Date findMaxDateWithCollections(List<Event> events) {
        if (events == null || events.isEmpty()) {
            return null;
        }
        return Collections.max(events.stream()
            .map(Event::getDate)
            .collect(Collectors.toList()));
    }

    static LocalDate findMaxLocalDateWithCollections(List<LocalEvent> events) {
        if (events == null || events.isEmpty()) {
            return null;
        }
        return Collections.max(events.stream()
            .map(LocalEvent::getDate)
            .collect(Collectors.toList()));
    }
}
