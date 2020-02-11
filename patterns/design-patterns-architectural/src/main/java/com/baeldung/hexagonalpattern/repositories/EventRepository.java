package com.baeldung.hexagonalpattern.repositories;

import com.baeldung.hexagonalpattern.entities.Event;

import java.util.List;

public interface EventRepository {

    void saveEvent(Event event);

    List<Event> getAllEvents();

}
