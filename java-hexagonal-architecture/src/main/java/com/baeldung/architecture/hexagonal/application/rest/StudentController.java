package com.baeldung.architecture.hexagonal.application.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.architecture.hexagonal.application.rest.payload.request.StudentEnrollmentRequest;
import com.baeldung.architecture.hexagonal.application.rest.payload.response.StudentActivityResponse;
import com.baeldung.architecture.hexagonal.application.rest.payload.response.StudentEnrollmentResponse;
import com.baeldung.architecture.hexagonal.domain.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(method = { RequestMethod.POST }, path = "/enrol")
    StudentEnrollmentResponse enrolStudent(
      @RequestBody StudentEnrollmentRequest request) {
        Long id = studentService.enrolStudent(request.getStudent());
        return new StudentEnrollmentResponse(id);
    }

    @RequestMapping(method = { RequestMethod.DELETE }, path = "/{id}")
    StudentActivityResponse unregisterStudent(@PathVariable Long id) {
        studentService.unregisterStudent(id);
        return new StudentActivityResponse("Operation succesful");
    }

    @RequestMapping(method = { RequestMethod.GET }, path = "/evaluate/{id}")
    StudentActivityResponse evaluatePerformance(@PathVariable Long id) {
        studentService.evaluatePerformance(id);
        return new StudentActivityResponse("Operation succesful");
    }
}