package com.baeldung.reactive.sse.service;

import com.baeldung.reactive.sse.model.EventSubscription;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EventSubscriptionsService {

    private List<EventSubscription> listeners;

    public EventSubscriptionsService() {
        this.listeners = new ArrayList<>();
    }

    public Flux<ServerSentEvent> subscribe() {
        EventSubscription e = new EventSubscription();
        listeners.add(e);

        return e.subscribe();
    }

    public void sendDateEvent(Date date) {
        for (EventSubscription e : listeners) {
            try {
                e.emit(ServerSentEvent.builder(date)
                        .event("date")
                        .id(UUID.randomUUID().toString())
                        .build());
            } catch (Exception ex) {
                listeners.remove(e);
            }
        }
    }
}
