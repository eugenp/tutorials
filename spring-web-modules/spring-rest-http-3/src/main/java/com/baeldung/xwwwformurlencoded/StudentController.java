package com.baeldung.xwwwformurlencoded;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    @PostMapping(path = "/simple",
        consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public ResponseEntity<StudentSimple> createStudentSimple(StudentSimple student) {
        return ResponseEntity.ok(student);
    }

    @PostMapping(path = "/complex",
        consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public ResponseEntity<StudentComplex> createStudentComplex(StudentComplex student) {
        return ResponseEntity.ok(student);
    }
}
