package com.baeldung.disruptor;

import com.lmax.disruptor.EventHandler;

public class SingleEventPrintConsumer implements EventConsumer {

    @Override
    @SuppressWarnings("unchecked")
    public EventHandler<ValueEvent>[] getEventHandler() {
        final EventHandler<ValueEvent> eventHandler = (event, sequence, endOfBatch) -> print(event.getValue(), sequence);
        return new EventHandler[] { eventHandler };
    }

    private void print(final int id, final long sequenceId) {
        System.out.println("Id is " + id + " sequence id that was used is " + sequenceId);
    }
}
