package com.sumanasaha.hexagonalarchitecturejava.inboundport;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sumanasaha.hexagonalarchitecturejava.domain.Student;


/**
 * @author ssaha (21.05.20)
 *
 * * This is a general interface for the REST API
 *
 */

public interface StudentRestUI {

    @PostMapping
    ResponseEntity<Void> addStudentDetails( @RequestBody Student student );

    @GetMapping( "/studentId" )
    Student getStudentDetails( @PathVariable String studentId );

    @GetMapping
    List<Student> listAllStudents();
}
