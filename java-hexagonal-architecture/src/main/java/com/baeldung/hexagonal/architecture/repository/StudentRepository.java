package com.baeldung.hexagonal.architecture.repository;

import java.util.List;
import java.util.Optional;

import com.baeldung.hexagonal.architecture.entity.Student;

public interface StudentRepository {

    void createStudent(Student student);

    Optional<Student> getStudent(int id);

    List<Student> getAllStudents();

}
