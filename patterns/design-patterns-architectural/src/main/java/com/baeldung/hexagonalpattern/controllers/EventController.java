package com.baeldung.hexagonalpattern.controllers;

import com.baeldung.hexagonalpattern.entities.Event;
import com.baeldung.hexagonalpattern.services.EventPersister;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/events")
public class EventController {

    public EventPersister eventPersister;

    public EventController(EventPersister eventPersister) {
        this.eventPersister = eventPersister;
    }

    @PostMapping
    public void persistEvent(@RequestBody Event event) {
        this.eventPersister.save(event);
    }
}
