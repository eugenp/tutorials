package com.baeldung.examples.r2dbc.flyway.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.examples.r2dbc.flyway.model.Department;
import com.baeldung.examples.r2dbc.flyway.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class DepartmentResource {

    private final DepartmentRepository departmentRepository;

    @GetMapping(path = "/department")
    public Mono<ResponseEntity<List<Department>>> getDepartments() {
        return departmentRepository.findAll()
          .collectList()
          .map(departments -> new ResponseEntity(departments, HttpStatus.OK));
    }

}
