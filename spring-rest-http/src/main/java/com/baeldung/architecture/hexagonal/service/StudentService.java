package com.baeldung.architecture.hexagonal.service;

import com.baeldung.architecture.hexagonal.dao.model.Student;

import java.util.Map;

public interface StudentService {

    void add(Student student);
    void delete(Student student);
    Map<Integer, Student> getAll();
}
