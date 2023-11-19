package com.baeldung.examples.r2dbc.flyway.rest;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.examples.r2dbc.flyway.model.Student;
import com.baeldung.examples.r2dbc.flyway.repository.StudentRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class StudentResource {

    private final StudentRepository studentRepository;

    @PostMapping(path = "/student")
    public Mono<ResponseEntity<Student>> createStudent(@RequestBody @Valid Mono<Student> createStudentRequest) {
        return createStudentRequest.flatMap(studentRepository::save)
          .map(student -> new ResponseEntity(student, HttpStatus.CREATED));
    }

}
