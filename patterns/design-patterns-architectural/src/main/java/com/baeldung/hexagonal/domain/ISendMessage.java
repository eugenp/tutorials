package com.baeldung.hexagonal.domain;

/**
 * A user sends a message (application's use case)
 */
public interface ISendMessage {
    void sendMessage(ChatUser from, ChatUser to, String message);
}
