package com.baeldung.hexagonalpattern.services;

import com.baeldung.hexagonalpattern.entities.Event;

public interface EventPersister {

    void save(Event event);
    
}
