package com.baeldung.global.exceptionhandler.handler;

public class RestResponse {

    String message;

    public RestResponse(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
