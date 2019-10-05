package com.baeldung.hexagonal.controller;

import com.baeldung.hexagonal.domain.port.IStudentPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyController {

    @Autowired
    private IStudentPort studentPort;

    @GetMapping("/students")
    public ResponseEntity<List> getStudents() {
        return ResponseEntity.ok(studentPort.getStudents());
    }

}
