package com.baeldung.reactive.eventstreaming;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NewsRepository {

    public List<NewsEvent> getEvents() {

        List<NewsEvent> events = new ArrayList<>();
        events.add(new NewsEvent(1, LocalDateTime.now(), "News event #1"));
        events.add(new NewsEvent(2, LocalDateTime.now(), "News event #2"));
        events.add(new NewsEvent(3, LocalDateTime.now(), "News event #3"));
        events.add(new NewsEvent(4, LocalDateTime.now(), "News event #4"));
        events.add(new NewsEvent(5, LocalDateTime.now(), "News event #5"));
        events.add(new NewsEvent(6, LocalDateTime.now(), "News event #6"));
        events.add(new NewsEvent(7, LocalDateTime.now(), "News event #7"));
        events.add(new NewsEvent(8, LocalDateTime.now(), "News event #8"));
        events.add(new NewsEvent(9, LocalDateTime.now(), "News event #9"));
        events.add(new NewsEvent(10, LocalDateTime.now(), "News event #10"));
        return events;
    }
}
