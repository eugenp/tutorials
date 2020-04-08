package com.baeldung.javahexagonal.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.javahexagonal.domain.Attendee;
import com.baeldung.javahexagonal.service.AttendeeAlreadyRegisteredException;
import com.baeldung.javahexagonal.service.EventService;

@RestController
@RequestMapping("registration")
public class EventRegistrationController {

    private final EventService service;

    public EventRegistrationController(EventService service) {
        this.service = service;
    }

    @PostMapping("{eventId}")
    public void registerAttendee(@PathVariable("eventId") String eventId, @RequestBody AttendeeData attendeeData) throws AttendeeAlreadyRegisteredException {
        Attendee attendee = new Attendee(attendeeData.getName(), attendeeData.getMail());
        service.registerAttendee(eventId, attendee);
    }
}
