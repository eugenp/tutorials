package com.baeldung.hexagonalpattern.services;

import com.baeldung.hexagonalpattern.entities.Event;
import com.baeldung.hexagonalpattern.repositories.EventRepository;
import com.baeldung.hexagonalpattern.repositories.MockEventRepository;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventServiceUnitTest {

    private static EventService eventService;

    @BeforeClass
    public static void setEventServiceInstance() {
        EventRepository eventRepository = new MockEventRepository();
        eventService = new EventService(eventRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenEventService_whenNullEventIsSaved_thenThrowIllegalArgumentException() {
        eventService.save(null);
    }

    @Test
    public void givenEventService_whenEventIsSaved_thenSuccess() {
        Event event = new Event(1, "Meeting");
        eventService.save(event);

        assertThat(eventService.getAllEvents().size()).isEqualTo(1);
    }
}
