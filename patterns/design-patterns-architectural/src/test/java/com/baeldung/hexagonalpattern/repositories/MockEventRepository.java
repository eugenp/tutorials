package com.baeldung.hexagonalpattern.repositories;

import com.baeldung.hexagonalpattern.entities.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MockEventRepository implements EventRepository {

    private List<Event> events = new ArrayList<>();

    @Override
    public void saveEvent(Event event) {
        this.events.add(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return Collections.unmodifiableList(this.events);
    }
}
