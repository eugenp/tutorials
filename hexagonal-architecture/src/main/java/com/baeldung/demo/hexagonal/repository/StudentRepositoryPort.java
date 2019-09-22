package com.baeldung.demo.hexagonal.repository;

import com.baeldung.demo.hexagonal.model.Student;

public interface StudentRepositoryPort {

    void create(String name);

    Student getStudent(Integer studentId);
}
