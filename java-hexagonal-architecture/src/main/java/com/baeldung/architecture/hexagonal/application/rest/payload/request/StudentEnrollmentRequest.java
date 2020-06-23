package com.baeldung.architecture.hexagonal.application.rest.payload.request;

import com.baeldung.architecture.hexagonal.domain.Student;

public class StudentEnrollmentRequest {

    private Student student;

    public StudentEnrollmentRequest() {
    }

    public StudentEnrollmentRequest(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }
}
