package com.baeldung.web.error;

public class CustomExceptionObject {

    private String message;

    public String getMessage() {
        return message;
    }

    public CustomExceptionObject setMessage(String message) {
        this.message = message;
        return this;
    }

}
