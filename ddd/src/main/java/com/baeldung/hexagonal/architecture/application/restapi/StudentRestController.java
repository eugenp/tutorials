package com.baeldung.hexagonal.architecture.application.restapi;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.baeldung.hexagonal.architecture.application.restapi.exceptions.StudentNotFoundException;
import com.baeldung.hexagonal.architecture.domain.models.Student;
import com.baeldung.hexagonal.architecture.domain.services.StudentServicePort;

@RestController
public class StudentRestController {

    private final StudentServicePort studentService;

    public StudentRestController(StudentServicePort studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        // TODO Auto-generated method stub
        return studentService.findAll();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id) {
        // TODO Auto-generated method stub
        Student student = studentService.findById(id)
            .orElseThrow(() -> new StudentNotFoundException("Studen with ID :" + id + " Not Found!"));

        return ResponseEntity.ok()
            .body(student);

    }

    @PostMapping("/students")
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        // TODO Auto-generated method stub
        Student std = studentService.save(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(std.getId())
            .toUri();

        return ResponseEntity.created(location)
            .build();
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {
        // TODO Auto-generated method stub
        Student student = studentService.findById(id)
            .orElseThrow(() -> new StudentNotFoundException("Studen with ID :" + id + " Not Found!"));

        studentService.delete(student.getId());
        return ResponseEntity.ok()
            .body("Student deleted with success!");
    }

}
