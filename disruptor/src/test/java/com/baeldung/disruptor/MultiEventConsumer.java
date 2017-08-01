package com.baeldung.disruptor;

import static org.junit.Assert.assertEquals;

import com.lmax.disruptor.EventHandler;

public class MultiEventConsumer implements EventConsumer {

    private int expectedValue = -1;
    private int otherExpectedValue = -1;

    @Override
    @SuppressWarnings("unchecked")
    public EventHandler<ValueEvent>[] getEventHandler() {
        final EventHandler<ValueEvent> eventHandler = (event, sequence, endOfBatch) -> assertExpectedValue(event.getValue());
        final EventHandler<ValueEvent> otherEventHandler = (event, sequence, endOfBatch) -> assertOtherExpectedValue(event.getValue());
        return new EventHandler[] { eventHandler, otherEventHandler };
    }

    private void assertExpectedValue(final int id) {
        assertEquals(++expectedValue, id);
    }

    private void assertOtherExpectedValue(final int id) {
        assertEquals(++otherExpectedValue, id);
    }
}
