package com.baeldung.pattern.cleanarchitecture.hexagonal.port.input;

import com.baeldung.pattern.cleanarchitecture.hexagonal.business.logic.FeedbackService;

public class CreationOverFakeMessagingQueue implements CreationOverMessagingQueue {

    private final FeedbackService feedbackService;

    public CreationOverFakeMessagingQueue(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Override
    public void listenForFeedback(String content) {
        feedbackService.createFeedback(content);
    }
}
