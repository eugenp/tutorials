package com.baeldung.architecture.controller;

import com.baeldung.architecture.model.StudentDto;
import com.baeldung.architecture.service.ServiceStudent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/student")
public class StudentController {
    private ServiceStudent serviceStudent;

    public StudentController(ServiceStudent serviceStudent) {
        this.serviceStudent = serviceStudent;
    }

    @GetMapping("/find-all")
    public List<StudentDto> findAll() {
        return serviceStudent.findStudents();
    }

    @PostMapping("/add")
    public StudentDto addStudent(@RequestBody StudentDto studentDto) {
        return serviceStudent.addStudent(studentDto);
    }
}
