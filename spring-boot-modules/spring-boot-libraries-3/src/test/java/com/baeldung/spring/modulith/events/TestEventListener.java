package com.baeldung.spring.modulith.events;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.baeldung.spring.modulith.events.orders.OrderCompletedEvent;

@Component
class TestEventListener {

    private final List<OrderCompletedEvent> events = new ArrayList<>();

    @EventListener
    void onEvent(OrderCompletedEvent event) {
        events.add(event);
    }

    public List<OrderCompletedEvent> getEvents() {
        return events;
    }

    public void reset() {
        events.clear();
    }
}

