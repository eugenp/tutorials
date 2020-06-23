package com.baeldung.architecture.hexagonal.domain.repository;

import java.util.Optional;

import com.baeldung.architecture.hexagonal.domain.Student;

//Outbound Port
public interface StudentRepository {

    public Student add(Student student);

    public Student update(Student student);

    public void deleteById(Long id);

    public Optional<Student> findById(Long id);
}
