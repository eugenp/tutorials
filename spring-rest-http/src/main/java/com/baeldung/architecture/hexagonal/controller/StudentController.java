package com.baeldung.architecture.hexagonal.controller;

import com.baeldung.architecture.hexagonal.dao.model.Student;
import com.baeldung.architecture.hexagonal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class StudentController {

    @PostConstruct
    public void init(){
        System.out.println("inside student controller");
    }

    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public void add(@RequestBody Student student) { studentService.add(student); }

    @DeleteMapping("/delete")
    public void delete(@RequestBody Student student) { studentService.delete(student); }

    @GetMapping("/get")
    public String getAll() {
        return studentService.getAll().toString();
    }
}
