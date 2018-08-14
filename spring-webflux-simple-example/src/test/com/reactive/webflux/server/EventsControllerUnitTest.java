package com.reactive.webflux.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EventsControllerUnitTest {

    @InjectMocks
    EventsController eventsController;

    @Test
    public void givenThatTheEventNumberIsOne_whenGetNextEventIsCalled_thenTheResultIsTheCorrectOne()  {
        eventsController.setEventNumber(1);

        String event = eventsController.getNextEvent();

        assertEquals(event, "Event: 1");
    }

} 
