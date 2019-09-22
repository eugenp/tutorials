package com.baeldung.demo.hexagonal.service;

import com.baeldung.demo.hexagonal.model.Student;

public interface StudentService {

    void create(String name);

    Student getStudent(Integer studentId);
}
