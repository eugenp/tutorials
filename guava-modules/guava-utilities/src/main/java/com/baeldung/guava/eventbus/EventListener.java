package com.baeldung.guava.eventbus;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventListener {

    private static int eventsHandled;
    private static final Logger LOG = LoggerFactory.getLogger(EventListener.class);

    @Subscribe
    public void stringEvent(String event) {
        LOG.info("do event [" + event + "]");
        eventsHandled++;
    }

    @Subscribe
    public void someCustomEvent(CustomEvent customEvent) {
        LOG.info("do event [" + customEvent.getAction() + "]");
        eventsHandled++;
    }

    @Subscribe
    public void handleDeadEvent(DeadEvent deadEvent) {
        LOG.info("unhandled event [" + deadEvent.getEvent() + "]");
        eventsHandled++;
    }

    int getEventsHandled() {
        return eventsHandled;
    }

    void resetEventsHandled() {
        eventsHandled = 0;
    }
}
