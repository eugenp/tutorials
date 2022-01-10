package com.baeldung.pattern.cleanarchitecture.hexagonal.port.input;

public interface CreationOverMessagingQueue {
    void listenForFeedback(String content);
}
