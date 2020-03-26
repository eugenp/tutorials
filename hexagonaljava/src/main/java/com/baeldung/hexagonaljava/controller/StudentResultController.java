package com.baeldung.hexagonaljava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonaljava.entity.Student;
import com.baeldung.hexagonaljava.service.StudentResultService;

@RestController
public class StudentResultController {

    @Autowired
    private StudentResultService studentResultService;

    @PostMapping(value = "/save")
    public void saveStudent(@RequestBody Student student) {
        studentResultService.save(student);
    }

    @GetMapping(value = "/getTotalMarks/{id}")
    public Double getTotalMarks(@PathVariable Integer id) {
        return studentResultService.getTotalMarks(id);
    }
}
