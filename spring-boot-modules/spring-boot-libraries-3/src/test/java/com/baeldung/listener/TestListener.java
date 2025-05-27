package com.baeldung.listener;

import java.util.ArrayList;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

public class TestListener {
    private List<String> articlesPublished = new ArrayList<>();
    private List<String> commentsAdded = new ArrayList<>();

    @KafkaListener(id = "test-id-1", topics = "baeldung.articles.published")
    public void listen(String event) {
        articlesPublished.add(event);
    }

    @KafkaListener(id = "test-id-2", topics = "baeldung.comment.added")
    public void commentsAddedListener(String event) {
        commentsAdded.add(event);
    }

    public List<String> getArticlesPublished() {
        return articlesPublished;
    }

    public List<String> getCommentsAdded() {
        return commentsAdded;
    }

    public void reset() {
        articlesPublished = new ArrayList<>();
        commentsAdded = new ArrayList<>();
    }
}