package com.baeldung.p6spy.controllers;

import com.baeldung.p6spy.repository.Student;
import com.baeldung.p6spy.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentRepository repository;

    @RequestMapping("/save")
    public Long save() {
        return repository.save(new Student("Pablo", "Picasso")).getId();
    }

    @RequestMapping("/find/{name}")
    public List<Student> getAll(@PathVariable String name) {
        return repository.findAllByFirstName(name);
    }

}
