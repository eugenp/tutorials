package com.baeldung.dapr.pubsub.subscriber;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;

@RestController
public class SubscriberRestController {

    private static final Logger logger = LoggerFactory.getLogger(SubscriberRestController.class);

    private List<CloudEvent<Order>> events = new ArrayList<>();

    @PostMapping("subscribe")
    @Topic(pubsubName = "pubsub", name = "topic")
    public void subscribe(@RequestBody CloudEvent<Order> cloudEvent) {
        logger.info("[bael] Order Event Received: {}", cloudEvent.getData());
        events.add(cloudEvent);
    }

    @GetMapping("events")
    public List<CloudEvent<Order>> getAllEvents() {
        return events;
    }
}
