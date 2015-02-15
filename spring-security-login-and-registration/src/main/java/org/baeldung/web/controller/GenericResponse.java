package org.baeldung.web.controller;

public class GenericResponse {

    private String message;

    public GenericResponse() {
        super();
    }

    public GenericResponse(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
