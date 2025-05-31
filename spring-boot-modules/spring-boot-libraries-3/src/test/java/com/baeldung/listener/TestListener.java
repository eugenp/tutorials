package com.baeldung.listener;

import java.util.ArrayList;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;

public class TestListener {
    private List<String> articlePublishedEvents = new ArrayList<>();
    private List<String> commentAddedEvents = new ArrayList<>();

    @KafkaListener(id = "test-id", topics = "baeldung.articles.published")
    public void listen(String event) {
        articlePublishedEvents.add(event);
    }

    @KafkaListener(id = "test-id-2", topics = "baeldung.comment.added")
    public void commentAddedEvents(String event) {
        commentAddedEvents.add(event);
    }

    public List<String> getArticlePublishedEvents() {
        return articlePublishedEvents;
    }

    public List<String> getCommentAddedEvents() {
        return commentAddedEvents;
    }

    public void reset() {
        articlePublishedEvents = new ArrayList<>();
        commentAddedEvents = new ArrayList<>();
    }
}