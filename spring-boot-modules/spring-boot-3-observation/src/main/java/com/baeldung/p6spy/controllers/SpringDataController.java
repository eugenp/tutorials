package com.baeldung.p6spy.controllers;

import com.baeldung.p6spy.repository.Student;
import com.baeldung.p6spy.repository.StudentRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("spring-jpa")
public class SpringDataController {

    private final StudentRepository repository;

    public SpringDataController(StudentRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/save")
    public Long save() {
        return repository.save(new Student("Pablo","Picasso")).getId();
    }

}
