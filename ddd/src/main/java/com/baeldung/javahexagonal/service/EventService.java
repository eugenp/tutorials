package com.baeldung.javahexagonal.service;

import org.springframework.stereotype.Service;

import com.baeldung.javahexagonal.domain.Attendee;
import com.baeldung.javahexagonal.domain.Event;
import com.baeldung.javahexagonal.domain.EventRepository;

@Service
public class EventService {

    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public void registerAttendee(String eventId, Attendee attendee) throws AttendeeAlreadyRegisteredException {
        Event event = repository.getEvent(eventId);
        for (Attendee registeredAttendee : repository.getAttendees(event)) {
            if (registeredAttendee.getName()
                .equals(attendee.getName())) {
                throw new AttendeeAlreadyRegisteredException(event, attendee);
            }
        }

        repository.addAttendee(event, attendee);
    }
}
