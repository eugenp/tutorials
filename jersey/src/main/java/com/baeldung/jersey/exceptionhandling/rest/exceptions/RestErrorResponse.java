package com.baeldung.jersey.exceptionhandling.rest.exceptions;

public class RestErrorResponse {
    private Object subject;
    private String message;

    public RestErrorResponse() {
    }

    public RestErrorResponse(String message) {
        this.message = message;
    }

    public RestErrorResponse(Object subject, String message) {
        this.subject = subject;
        this.message = message;
    }

    public Object getSubject() {
        return subject;
    }

    public void setSubject(Object subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
