package com.baeldung.assistant;

/**
 * Answer model. Contains all data required for rendering Assistant's answer.
 */
public class Model {
    private String textMessage;

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
