package com.baeldung.architecture.hexagonal.dao;

import com.baeldung.architecture.hexagonal.dao.model.Student;

import java.util.Map;

public interface StudentRepository {

    void add(Student student);
    void delete(Student student);
    Map<Integer, Student> getAll();
}
