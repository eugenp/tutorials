package com.baeldung.service;

import java.util.List;

import com.baeldung.model.Student;

public interface StudentService {
    public List<Student> findAll();

    public void save(Student student);
}
