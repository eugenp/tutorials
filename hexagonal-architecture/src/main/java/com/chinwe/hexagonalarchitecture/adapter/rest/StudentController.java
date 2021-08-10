package com.chinwe.hexagonalarchitecture.adapter.rest;

import com.chinwe.hexagonalarchitecture.domain.model.Student;
import com.chinwe.hexagonalarchitecture.domain.responsestatus.Status;
import com.chinwe.hexagonalarchitecture.port.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        return new ResponseEntity<>(studentService.getStudents(), HttpStatus.OK);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        return new ResponseEntity<>(studentService.getStudentById(studentId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Status> addStudent(@RequestBody Student student) {
        Status status=studentService.addStudent(student);
        if(status.getStatusCode()==400){
            return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Status> removeStudent(@PathVariable Long studentId) {
        Status status=studentService.removeStudent(studentId);
        if(status.getStatusCode()==404){
            return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
