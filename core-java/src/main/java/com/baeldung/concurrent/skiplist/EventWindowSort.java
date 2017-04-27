package com.baeldung.concurrent.skiplist;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.ToLongFunction;

public class EventWindowSort {
    private final ConcurrentSkipListMap<ZonedDateTime, String> events = new ConcurrentSkipListMap<>(Comparator.comparingLong(new ToLongFunction<ZonedDateTime>() {
        @Override
        public long applyAsLong(ZonedDateTime value) {
            return value
              .toInstant()
              .toEpochMilli();
        }
    }));

    public void acceptEvent(Event event) {
        events.put(event.getEventTime(), event.getContent());
    }

    public ConcurrentNavigableMap<ZonedDateTime, String> getEventsFromLastMinute() {
        return events.tailMap(ZonedDateTime
          .now()
          .minusMinutes(1));
    }

    public ConcurrentNavigableMap<ZonedDateTime, String> getEventsOlderThatOneMinute() {
        return events.headMap(ZonedDateTime
          .now()
          .minusMinutes(1));
    }

}

