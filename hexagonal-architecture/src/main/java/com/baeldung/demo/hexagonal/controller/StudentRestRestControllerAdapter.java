package com.baeldung.demo.hexagonal.controller;

import com.baeldung.demo.hexagonal.model.Student;
import com.baeldung.demo.hexagonal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentRestRestControllerAdapter implements StudentRestUI {

    @Autowired
    private StudentService studentService;

    @Override
    public void create(Student request) {
        studentService.create(request.getName());
    }

    @Override
    public Student getStudentById(Integer studentId) {
        return studentService.getStudent(studentId);
    }
}
