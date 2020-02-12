package com.baeldung.hexagonalpattern.services;

import com.baeldung.hexagonalpattern.entities.Event;
import com.baeldung.hexagonalpattern.repositories.EventRepository;

import java.util.Collections;
import java.util.List;

public class EventService {

    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void save(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Empty event");
        }
        this.eventRepository.saveEvent(event);
    }

    public List<Event> getAllEvents() {
        List<Event> events = this.eventRepository.getAllEvents();
        return events.isEmpty() ? Collections.emptyList() : events;
    }
}
