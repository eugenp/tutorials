package com.baeldung.javahexagonal.service;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.javahexagonal.domain.Attendee;
import com.baeldung.javahexagonal.domain.Event;
import com.baeldung.javahexagonal.domain.EventRepository;

class EventServiceUnitTest {

    private EventRepository repository;

    private EventService service;

    @BeforeEach
    void setUp() {
        repository = mock(EventRepository.class);
        service = new EventService(repository);
    }

    @Test
    void registerAttendee() throws AttendeeAlreadyRegisteredException {
        Event event = new Event("junit", "Introduction to JUnit");
        doReturn(event).when(repository)
            .getEvent("junit");

        Attendee registeredAttendee = new Attendee("John Doe", "you@example.org");
        doReturn(asList(registeredAttendee)).when(repository)
            .getAttendees(event);

        Attendee newAttendee = new Attendee("Jane Doe", "info@example.org");
        service.registerAttendee("junit", newAttendee);

        verify(repository).addAttendee(event, newAttendee);
    }

    @Test
    void registerAttendee_withAlreadyRegisteredAttendee() {
        Event event = new Event("junit", "Introduction to JUnit");
        doReturn(event).when(repository)
            .getEvent("junit");

        Attendee registeredAttendee = new Attendee("John Doe", "you@example.org");
        doReturn(asList(registeredAttendee)).when(repository)
            .getAttendees(event);

        assertThatThrownBy(() -> service.registerAttendee("junit", registeredAttendee)).isInstanceOf(AttendeeAlreadyRegisteredException.class);

        verify(repository, times(0)).addAttendee(event, registeredAttendee);
    }
}
