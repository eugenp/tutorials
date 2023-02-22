package com.baeldung.controller.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.baeldung.controller.student.Student;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping(value = "/student/{studentId}")
    public Student getTestData(@PathVariable Integer studentId) {
        Student student = new Student();
        student.setName("Peter");
        student.setId(studentId);

        return student;

    }
}
