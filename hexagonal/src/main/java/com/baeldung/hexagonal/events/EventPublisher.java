package com.baeldung.hexagonal.events;

import com.baeldung.hexagonal.model.Item;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {
    public void publish(Item retrieved) {
        //do something
    }
}
