package com.baeldung.hexagonalarch.controller;

import com.baeldung.hexagonalarch.domain.model.Student;
import com.baeldung.hexagonalarch.service.StudentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(final StudentService studentService) {
      this.studentService = studentService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Student addStudent(@RequestBody Student student) {
      return studentService.addStudent(student);
    }

    @GetMapping(path = "/{studentId}")
    public Student getStudent(@PathVariable("studentId") Long studentId) {
      return studentService.getStudent(studentId);
    }

    @GetMapping(path = "/all")
    public List<Student> getAllStudents() {
      return studentService.getAllStudents();
    }

}
