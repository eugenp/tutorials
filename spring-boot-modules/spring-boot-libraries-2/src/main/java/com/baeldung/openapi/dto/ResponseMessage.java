package com.baeldung.openapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A class representing a response message with a single field for the message content.
 */
@AllArgsConstructor
@Getter
public class ResponseMessage {
    /**
     * The content of the response message.
     */
    private String message;
}