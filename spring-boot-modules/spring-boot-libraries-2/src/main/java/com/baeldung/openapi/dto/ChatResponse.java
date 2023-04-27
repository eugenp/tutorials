package com.baeldung.openapi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * A data transfer object representing a chat response.
 * This class contains a list of choices, each of which includes an index and a message.
 */
@Getter
@Setter
@ToString
public class ChatResponse {
    /**
     * The list of choices in the chat response from OpenAI API.
     */
    private List<Choice> choices;

    /**
     * A nested class representing a choice in the chat response from OpenAI API.
     * Each choice includes an index and a message.
     */
    @ToString
    @Getter
    public static class Choice {
        /**
         * The index of the choice.
         */
        private int index;
        /**
         * The message associated with the choice.
         */
        private Message message;
    }
}