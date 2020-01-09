package com.baeldung.hexagonal.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.domain.Student;
import com.baeldung.hexagonal.port.StudentService;



@RestController
@RequestMapping("/students")
public class StudentController {

    // get access to the application's ports
    @Autowired
    private StudentService service;

    @GetMapping("/")
    public List<Student> read() {
        return service.readAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> read(@PathVariable("id") Long id) {
        // use the port
        Student foundStudent = service.read(id);
        
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundStudent);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Student> create(@RequestBody Student student) {
        // use the port
        Student createdStudent = service.create(student); 
        
        // conversion back to the outside world of HTTP
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@RequestBody Student student, @PathVariable Long id) {
        // use the port
        Student updatedStudent = service.update(id, student);
        
        // conversion back to the outside world of HTTP
        if (updatedStudent == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedStudent);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

}
