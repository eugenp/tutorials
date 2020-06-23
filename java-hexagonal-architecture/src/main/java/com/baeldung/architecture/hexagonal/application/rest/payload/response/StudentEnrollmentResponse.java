package com.baeldung.architecture.hexagonal.application.rest.payload.response;

public class StudentEnrollmentResponse {

    private final Long id;

    public StudentEnrollmentResponse(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
