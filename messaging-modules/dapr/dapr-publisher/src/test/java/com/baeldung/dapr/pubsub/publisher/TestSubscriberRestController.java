package com.baeldung.dapr.pubsub.publisher;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;

@RestController
public class TestSubscriberRestController {

    private List<CloudEvent<Order>> events = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(TestSubscriberRestController.class);

    @PostMapping("subscribe")
    @Topic(pubsubName = "pubsub", name = "topic")
    public void subscribe(@RequestBody CloudEvent<Order> cloudEvent) {
        logger.info("[bael] Test Order Event Received: {}", cloudEvent.getData());
        events.add(cloudEvent);
    }

    public List<CloudEvent<Order>> getAllEvents() {
        return events;
    }
}
