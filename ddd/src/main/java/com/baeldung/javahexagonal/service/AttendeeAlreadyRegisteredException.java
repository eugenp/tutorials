package com.baeldung.javahexagonal.service;

import com.baeldung.javahexagonal.domain.Attendee;
import com.baeldung.javahexagonal.domain.Event;

public class AttendeeAlreadyRegisteredException extends Exception {

    private final Event event;

    private final Attendee attendee;

    public AttendeeAlreadyRegisteredException(Event event, Attendee attendee) {
        super(attendee + " is already registered for " + event);
        this.event = event;
        this.attendee = attendee;
    }

    public Event getEvent() {
        return event;
    }

    public Attendee getAttendee() {
        return attendee;
    }
}
