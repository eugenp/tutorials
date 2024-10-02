package com.splunk.log4j.controller;

import com.splunk.log4j.dto.Student;
import com.splunk.log4j.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("{rollNo}")
    public Student getStudent(@PathVariable int rollNo) {
        return studentService.getStudent(rollNo);
    }
}
