package com.baeldung.hexagonal.architecture.domain.service;

import java.util.List;
import java.util.Optional;

import com.baeldung.hexagonal.architecture.domain.model.Student;

public interface StudentServicePort {
    List<Student> findAll();

    Optional<Student> findById(int id);

    Student save(Student std);

    void delete(int id);

}
