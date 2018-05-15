package com.baeldung.webfluxdemo.controller;

import com.baeldung.webfluxdemo.model.Event;
import com.baeldung.webfluxdemo.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class EventController {

    @Autowired
    private EventRepo eventRepository;

    @GetMapping("/events")
    public Flux<Event> getAllEvents(){
        return eventRepository.findAll();
    }

    @PostMapping("/events")
    public Mono<Event> saveEvent(@Valid @RequestBody Event event) {
        return eventRepository.save(event);
    }

    @GetMapping("/events/{id}")
    public Mono<ResponseEntity<Event>> getEventById(@PathVariable(value="id") String eventId){
        return eventRepository.findById(eventId)
                .map(savedEvent -> ResponseEntity.ok(savedEvent))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/events/{id}")
    public Mono<ResponseEntity<Void>> deleteEvent(
            @PathVariable(value = "id") String eventId) {
        return eventRepository.findById(eventId)
                .flatMap(existingEvent ->
                        eventRepository.delete(existingEvent)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/stream/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Event> streamAllEvents() {
        return eventRepository.findAll();
    }
}
