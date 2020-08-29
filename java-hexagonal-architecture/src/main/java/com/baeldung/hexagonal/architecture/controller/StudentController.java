package com.baeldung.hexagonal.architecture.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.architecture.entity.Student;
import com.baeldung.hexagonal.architecture.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable("id") int id) {
        Optional<Student> student = studentService.getStudent(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        studentService.createStudent(student);
        return ResponseEntity.ok().build();
    }

}
