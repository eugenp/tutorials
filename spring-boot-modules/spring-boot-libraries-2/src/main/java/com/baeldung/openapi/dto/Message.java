package com.baeldung.openapi.dto;

import lombok.*;

/**
 * A data transfer object representing a message in a chat conversation.
 * This class contains a role and content string, which are used to represent a message in a chat conversation.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
    /**
     * The role associated with the message.
     */
    private String role;

    /**
     * The content of the message.
     */
    private String content;
}