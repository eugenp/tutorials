package org.baeldung.guava;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GuavaEventBusTest {

    private EventListener listener;

    @Before
    public void setUp() {
        listener = new EventListener();
        EventBusWrapper.register(listener);
    }

    @After
    public void tearDown() {
        EventBusWrapper.unregister(listener);
    }

    @Test
    public void givenStringEvent_whenEventHandled_thenSuccess() {
        listener.resetEventsHandled();

        EventBusWrapper.post("String Event");
        assertEquals(1, listener.getEventsHandled());

    }

    @Test
    public void givenCustomEvent_whenEventHandled_thenSuccess() {
        listener.resetEventsHandled();

        CustomEvent customEvent = new CustomEvent("Custom Event");
        EventBusWrapper.post(customEvent);

        assertEquals(1, listener.getEventsHandled());
    }
}
