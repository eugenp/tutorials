package com.baeldung.mbassador;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.common.DeadMessage;
import net.engio.mbassy.bus.common.FilteredMessage;
import net.engio.mbassy.listener.Filter;
import net.engio.mbassy.listener.Filters;
import net.engio.mbassy.listener.Handler;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MBassadorFilterUnitTest {

    private MBassador dispatcher = new MBassador();

    private Message baseMessage;
    private Message subMessage;
    private String testString;
    private FilteredMessage filteredMessage;
    private RejectMessage rejectMessage;
    private DeadMessage deadMessage;

    @Before
    public void prepareTests() {
        dispatcher.subscribe(this);
    }

    @Test
    public void whenMessageDispatched_thenMessageFiltered() {
        dispatcher.post(new Message()).now();
        assertNotNull(baseMessage);
        assertNull(subMessage);
    }

    @Test
    public void whenRejectDispatched_thenRejectFiltered() {
        dispatcher.post(new RejectMessage()).now();
        assertNotNull(subMessage);
        assertNull(baseMessage);
    }

    @Test
    public void whenShortStringDispatched_thenStringHandled() {
        dispatcher.post("foobar").now();
        assertNotNull(testString);
    }

    @Test
    public void whenLongStringDispatched_thenStringFiltered() {
        dispatcher.post("foobar!").now();
        assertNull(testString);
        // filtered only populated when messages does not pass any filters
        assertNotNull(filteredMessage);
        assertTrue(filteredMessage.getMessage() instanceof String);
        assertNull(deadMessage);
    }

    @Test
    public void whenWrongRejectDispatched_thenRejectFiltered() {
        RejectMessage testReject = new RejectMessage();
        testReject.setCode(-1);
        dispatcher.post(testReject).now();
        assertNull(rejectMessage);
        assertNotNull(subMessage);
        assertEquals(-1, ((RejectMessage) subMessage).getCode());
    }

    @Handler(filters = { @Filter(Filters.RejectSubtypes.class) })
    public void handleBaseMessage(Message message) {
        this.baseMessage = message;
    }

    @Handler(filters = { @Filter(Filters.SubtypesOnly.class) })
    public void handleSubMessage(Message message) {
        this.subMessage = message;
    }

    @Handler(condition = "msg.length() < 7")
    public void handleStringMessage(String message) {
        this.testString = message;
    }

    @Handler(condition = "msg.getCode() != -1")
    public void handleRejectMessage(RejectMessage rejectMessage) {
        this.rejectMessage = rejectMessage;
    }

    @Handler
    public void handleFilterMessage(FilteredMessage message) {
        this.filteredMessage = message;
    }

    @Handler
    public void handleDeadMessage(DeadMessage deadMessage) {
        this.deadMessage = deadMessage;
    }

}
