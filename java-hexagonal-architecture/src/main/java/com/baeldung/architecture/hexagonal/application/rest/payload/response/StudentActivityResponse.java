package com.baeldung.architecture.hexagonal.application.rest.payload.response;

public class StudentActivityResponse {

    private String message;

    public StudentActivityResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
