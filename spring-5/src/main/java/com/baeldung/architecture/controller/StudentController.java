package com.baeldung.architecture.controller;

import com.baeldung.architecture.model.StudentDto;
import com.baeldung.architecture.service.ServiceStudentBusiness;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/student")
public class StudentController {
    private ServiceStudentBusiness serviceStudentBusiness;

    public StudentController(ServiceStudentBusiness serviceStudentBusiness) {
        this.serviceStudentBusiness = serviceStudentBusiness;
    }

    @GetMapping("/find-all")
    public List<StudentDto> findAll() {
        return serviceStudentBusiness.findStudents();
    }

    @PostMapping("/add")
    public StudentDto addStudent(@RequestBody StudentDto studentDto) {
        return serviceStudentBusiness.addStudent(studentDto);
    }
}
