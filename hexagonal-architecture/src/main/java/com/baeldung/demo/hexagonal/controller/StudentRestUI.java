package com.baeldung.demo.hexagonal.controller;

import com.baeldung.demo.hexagonal.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface StudentRestUI {

    @PostMapping
    void create(@RequestBody Student request);

    @GetMapping("/{studentId}")
    Student getStudentById(@PathVariable Integer studentId);
}
