package com.baeldung.concurrent.skiplist;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

class EventWindowSort {
    private final ConcurrentSkipListMap<ZonedDateTime, String> events
      = new ConcurrentSkipListMap<>(Comparator.comparingLong(value -> value.toInstant().toEpochMilli()));

    void acceptEvent(Event event) {
        events.put(event.getEventTime(), event.getContent());
    }

    ConcurrentNavigableMap<ZonedDateTime, String> getEventsFromLastMinute() {
        return events.tailMap(ZonedDateTime
          .now()
          .minusMinutes(1));
    }

    ConcurrentNavigableMap<ZonedDateTime, String> getEventsOlderThatOneMinute() {
        return events.headMap(ZonedDateTime
          .now()
          .minusMinutes(1));
    }

}

