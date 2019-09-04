package com.baeldung.restassured.learner;

class CourseNotFoundException extends RuntimeException {

    CourseNotFoundException(String code) {
        super(code);
    }
}
