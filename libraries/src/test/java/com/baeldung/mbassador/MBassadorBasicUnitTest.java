package com.baeldung.mbassador;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.common.DeadMessage;
import net.engio.mbassy.listener.Handler;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MBassadorBasicUnitTest {

    private MBassador dispatcher = new MBassador();

    private String messageString;
    private Integer messageInteger;
    private Object deadEvent;

    @Before
    public void prepareTests() {
        dispatcher.subscribe(this);
    }

    @Test
    public void whenStringDispatched_thenHandleString() {
        dispatcher.post("TestString").now();
        assertNotNull(messageString);
        assertEquals("TestString", messageString);
    }

    @Test
    public void whenIntegerDispatched_thenHandleInteger() {
        dispatcher.post(42).now();
        assertNull(messageString);
        assertNotNull(messageInteger);
        assertTrue(42 == messageInteger);
    }

    @Test
    public void whenLongDispatched_thenDeadEvent() {
        dispatcher.post(42L).now();
        assertNull(messageString);
        assertNull(messageInteger);
        assertNotNull(deadEvent);
        assertTrue(deadEvent instanceof Long);
        assertTrue(42L == (Long) deadEvent);
    }

    @Handler
    public void handleString(String message) {
        messageString = message;
    }

    @Handler
    public void handleInteger(Integer message) {
        messageInteger = message;
    }

    @Handler
    public void handleDeadEvent(DeadMessage message) {

        deadEvent = message.getMessage();

    }

}
