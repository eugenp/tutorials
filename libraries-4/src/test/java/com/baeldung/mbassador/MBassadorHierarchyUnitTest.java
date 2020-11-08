package com.baeldung.mbassador;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.listener.Handler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MBassadorHierarchyUnitTest {

    private MBassador dispatcher = new MBassador<Message>();

    private Message message;
    private AckMessage ackMessage;
    private RejectMessage rejectMessage;

    @Before
    public void prepareTests() {
        dispatcher.subscribe(this);
    }

    @Test
    public void whenMessageDispatched_thenMessageHandled() {
        dispatcher.post(new Message()).now();
        assertNotNull(message);
        assertNull(ackMessage);
        assertNull(rejectMessage);
    }

    @Test
    public void whenRejectDispatched_thenMessageAndRejectHandled() {
        dispatcher.post(new RejectMessage()).now();
        assertNotNull(message);
        assertNotNull(rejectMessage);
        assertNull(ackMessage);
    }

    @Test
    public void whenAckDispatched_thenMessageAndAckHandled() {
        dispatcher.post(new AckMessage()).now();
        assertNotNull(message);
        assertNotNull(ackMessage);
        assertNull(rejectMessage);
    }

    @Handler
    public void handleMessage(Message message) {
        this.message = message;
    }

    @Handler
    public void handleRejectMessage(RejectMessage message) {
        rejectMessage = message;
    }

    @Handler
    public void handleAckMessage(AckMessage message) {
        ackMessage = message;
    }
}
