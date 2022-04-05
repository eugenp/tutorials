package com.baeldung.restassured.learner;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(assignableTypes = CourseController.class)
public class CourseControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CourseNotFoundException.class)
    @SuppressWarnings("ThrowablePrintedToSystemOut")
    public void handleCourseNotFoundException(CourseNotFoundException cnfe) {
        System.out.println(cnfe);
    }
}
