package com.baeldung.spring.modulith.externalization.listener;

import java.util.ArrayList;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TestListener {
    private List<String> events = new ArrayList<>();

    @KafkaListener(id = "test-id", topics = "baeldung.articles.published")
    public void listen(String event) {
        events.add(event);
    }

    public List<String> getEvents() {
        return events;
    }

    public void reset() {
        events = new ArrayList<>();
    }
}