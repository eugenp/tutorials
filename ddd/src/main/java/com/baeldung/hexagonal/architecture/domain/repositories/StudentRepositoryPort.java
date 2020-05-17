package com.baeldung.hexagonal.architecture.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.baeldung.hexagonal.architecture.domain.models.Student;

public interface StudentRepositoryPort {
    
    List<Student> findAll();
    Optional<Student> findById(int id);
    Student save(Student std);
    void delete(int id);

}
