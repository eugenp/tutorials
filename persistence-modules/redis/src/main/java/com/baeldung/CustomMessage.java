package com.baeldung;

import java.io.Serializable;

/**
 * Created by johnson on 3/9/17.
 */
public class CustomMessage implements Serializable {
    private String message;

    public CustomMessage() {
    }

    public CustomMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
