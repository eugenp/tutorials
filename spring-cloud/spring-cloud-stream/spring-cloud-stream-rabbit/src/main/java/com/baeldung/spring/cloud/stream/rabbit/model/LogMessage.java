package com.baeldung.spring.cloud.stream.rabbit.model;

import java.io.Serializable;

public class LogMessage implements Serializable {

    private static final long serialVersionUID = -5857383701708275796L;

    private String message;

    public LogMessage() {

    }

    public LogMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
