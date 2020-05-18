package com.baeldung.hexagonal.architecture.domain.service;

import java.util.List;
import java.util.Optional;

import com.baeldung.hexagonal.architecture.domain.model.Student;
import com.baeldung.hexagonal.architecture.domain.repository.StudentRepositoryPort;

public class DomainStudentService implements StudentServicePort {

    private StudentRepositoryPort studentRepositoryPort;

    public DomainStudentService(StudentRepositoryPort studentRepositoryPort) {
        this.studentRepositoryPort = studentRepositoryPort;
    }

    @Override
    public List<Student> findAll() {
        return studentRepositoryPort.findAll();
    }

    @Override
    public Optional<Student> findById(int id) {
        return studentRepositoryPort.findById(id);
    }

    @Override
    public Student save(Student std) {
        return studentRepositoryPort.save(std);
    }

    @Override
    public void delete(int id) {
        studentRepositoryPort.delete(id);
    }

}
