package com.baeldung.hexagonal.architecture.domain.services;

import java.util.List;
import java.util.Optional;

import com.baeldung.hexagonal.architecture.domain.models.Student;
import com.baeldung.hexagonal.architecture.domain.repositories.StudentRepositoryPort;

public class DomainStudentService implements StudentServicePort {
    
    private StudentRepositoryPort studentRepositoryPort;
    
    public DomainStudentService(StudentRepositoryPort studentServicePort) {
        this.studentRepositoryPort = studentServicePort;
    }

    @Override
    public List<Student> findAll() {
        // TODO Auto-generated method stub
        return studentRepositoryPort.findAll();
    }

    @Override
    public Optional<Student> findById(int id) {
        // TODO Auto-generated method stub
        return studentRepositoryPort.findById(id);
    }

    @Override
    public Student save(Student std) {
        // TODO Auto-generated method stub
        return studentRepositoryPort.save(std);
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        studentRepositoryPort.delete(id);
    }

}
