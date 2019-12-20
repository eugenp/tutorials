package com.baeldung.guava;

import com.google.common.eventbus.EventBus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GuavaEventBusUnitTest {

    private EventListener listener;
    private EventBus eventBus;

    @Before
    public void setUp() {
        eventBus = new EventBus();
        listener = new EventListener();

        eventBus.register(listener);
    }

    @After
    public void tearDown() {
        eventBus.unregister(listener);
    }

    @Test
    public void givenStringEvent_whenEventHandled_thenSuccess() {
        listener.resetEventsHandled();

        eventBus.post("String Event");
        assertEquals(1, listener.getEventsHandled());
    }

    @Test
    public void givenCustomEvent_whenEventHandled_thenSuccess() {
        listener.resetEventsHandled();

        CustomEvent customEvent = new CustomEvent("Custom Event");
        eventBus.post(customEvent);

        assertEquals(1, listener.getEventsHandled());
    }

    @Test
    public void givenUnSubscribedEvent_whenEventHandledByDeadEvent_thenSuccess() {
        listener.resetEventsHandled();

        eventBus.post(12345);
        assertEquals(1, listener.getEventsHandled());
    }

}
