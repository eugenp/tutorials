package com.baeldung.javahexagonal.domain;

import java.util.List;

public interface EventRepository {

    Event getEvent(String id);

    List<Attendee> getAttendees(Event event);

    void addAttendee(Event event, Attendee attendee);
}
