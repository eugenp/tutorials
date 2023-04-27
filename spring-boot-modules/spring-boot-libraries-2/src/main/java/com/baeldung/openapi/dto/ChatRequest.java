package com.baeldung.openapi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * A data transfer object representing a chat request.
 * This class contains a model name and a list of messages, which is used to represent a request for a chat conversation.
 */
@Setter
@Getter
@ToString
public class ChatRequest {
    /**
     * A constant string value representing the user role.
     * This string is used to indicate the role of a user in the for the chat.
     */
    private static final String USER_ROLE = "user";

    /**
     * The name of the model to use for the chat conversation.
     */
    private String model;
    /**
     * The list of messages in the chat conversation.
     */
    private List<Message> messages;

    /**
     * Creates a new ChatRequest instance with the specified model name and prompt message.
     *
     * @param model  the name of the model to use for the chat conversation
     * @param prompt the prompt message for the conversation
     */
    public ChatRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message(USER_ROLE, prompt));
    }
}